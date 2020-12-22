package fr.minemobs.discordbot.PufferTeamMain.listener;

import fr.minemobs.discordbot.PufferTeamMain.Infos;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Listener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        System.out.printf("%#s est pret \n", event.getJDA().getSelfUser());
    }

    ArrayList<String> joinMsg = new ArrayList<>();

    public Listener(){
        joinMsg.add("%s joined the game");
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        String member = event.getUser().getAsMention();

        Random random = new Random();

        String[] joinMsgArray = joinMsg.toArray(new String[joinMsg.size()]);

        String sentMeg = String.format(joinMsgArray[random.nextInt(joinMsgArray.length)], event.getUser().getAsMention());

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
                }else{
                    event.getMessage().reply("Le message ne contient pas l'argument **%s**").queue();
                    event.getMessage().addReaction("❌").queue();
                }
            }else if(event.getMessage().getContentRaw().equalsIgnoreCase(Infos.PREFIX.getS() + "ping")){
                event.getChannel().sendMessage("Discord : " + event.getJDA().getGatewayPing() + " ms").queue();
                event.getChannel().sendMessage("Api : " + event.getJDA().getRestPing().complete() + " ms").queue();
            }
        }else{
            event.getMessage().reply("Je n'accepte pas les DMs \n" +
                    "I don't accept DMs").queue();
        }
    }
}
