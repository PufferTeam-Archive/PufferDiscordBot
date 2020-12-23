package fr.minemobs.discordbot.PufferTeamMain.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.minemobs.discordbot.PufferTeamMain.Infos;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Listener extends ListenerAdapter {

    ArrayList<String> joinMsg = new ArrayList<>();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        System.out.printf("%#s est pret \n", event.getJDA().getSelfUser());
        try {
            joinMsg = readJsonForJoinMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!joinMsg.contains("%s joined the game")){
            joinMsg.add("%s joined the game");
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        String member = event.getUser().getAsMention();

        Random random = new Random();

        String[] joinMsgArray = joinMsg.toArray(new String[joinMsg.size()]);

        String sentMeg = String.format(joinMsgArray[random.nextInt(joinMsgArray.length)], member);

        TextChannel tc = event.getGuild().getSystemChannel();
        tc.sendMessage(sentMeg).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        if(event.isFromGuild()){
            String[] args = event.getMessage().getContentRaw().split(" ");
            if(args[0].equalsIgnoreCase(Infos.PREFIX.getS() + "addJoinMessage")){
                if(!event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.ADMINISTRATOR)){
                    event.getMessage().reply("Vous n'avez pas la permission d'utiliser cette commande").queue();
                }
                if(event.getMessage().getContentRaw().contains("%s")){
                    String formattedMessage = event.getMessage().getContentRaw().replace(args[0], "");
                    if(joinMsg.contains(formattedMessage)){
                        event.getMessage().reply("Ce message est déjà enrengistré").queue();
                        return;
                    }
                    event.getChannel().sendMessage("Ok le message à été ajouté").queue();
                    event.getMessage().addReaction("✔").queue();
                    joinMsg.add(formattedMessage);
                    createJsonForJoinMessages();
                }else{
                    event.getMessage().reply("Le message ne contient pas l'argument **%s**").queue();
                    event.getMessage().addReaction("❌").queue();
                }
            }else if(event.getMessage().getContentRaw().equalsIgnoreCase(Infos.PREFIX.getS() + "ping")){
                event.getChannel().sendMessage("Discord : " + event.getJDA().getGatewayPing() + " ms").queue();
                event.getChannel().sendMessage("Api : " + event.getJDA().getRestPing().complete() + " ms").queue();
            }else if(args[0].equalsIgnoreCase(Infos.PREFIX.getS() + "refreshJoinMessage")){
                if(event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.ADMINISTRATOR)){
                    joinMsg.clear();
                    try {
                        joinMsg.addAll(readJsonForJoinMessages());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    event.getMessage().reply("Le json à bien été refresh").queue();
                    event.getMessage().addReaction("✔").queue();
                }else{
                    event.getMessage().reply("You don't have the permission to use this command").queue();
                    event.getMessage().addReaction("❌").queue();
                }
            }
        }
    }

    private ArrayList<String> readJsonForJoinMessages() throws IOException {

        File file = new File("test.json");

        Gson gson = new Gson();

        Reader reader = Files.newBufferedReader(file.toPath());

        ArrayList<String> msg = new ArrayList<>();

        String[] welcomeMessage = gson.fromJson(reader, String[].class);
        Collections.addAll(msg, welcomeMessage);
        return msg;
    }

    private void createJsonForJoinMessages() {
        try{
            File file = new File("test.json");

            if(!file.exists()) file.createNewFile();

            ArrayList<String> stringArray = new ArrayList<>();

            for (String s : joinMsg) {
                if(!readJsonForJoinMessages().contains(s)){
                    stringArray.add(s);
                }
            }

            Writer writer = Files.newBufferedWriter(Paths.get(file.toURI()));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(stringArray, writer);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
