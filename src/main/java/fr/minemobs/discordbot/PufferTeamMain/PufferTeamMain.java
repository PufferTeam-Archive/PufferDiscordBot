package fr.minemobs.discordbot.PufferTeamMain;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import fr.minemobs.discordbot.PufferTeamMain.commands.PingCommand;
import fr.minemobs.discordbot.PufferTeamMain.commands.SearchRepo;
import fr.minemobs.discordbot.PufferTeamMain.commands.SetStatusCmd;
import fr.minemobs.discordbot.PufferTeamMain.listener.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PufferTeamMain {

    public static Logger LOGGER = LoggerFactory.getLogger(PufferTeamMain.class);
    public static JDA jda;
    public static CommandClientBuilder client;

    public static void main(String[] args) {
        try{
            if(args[0].toLowerCase().contains("DISCORD_TOKEN=".toLowerCase())){
                String token = args[0].replace("DISCORD_TOKEN=", "");
                launchDiscordBot(token);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            LOGGER.error("Veuillez relancer le bot avec l'arguement : DISCORD_TOKEN=");
        }
    }

    private static void launchDiscordBot(String token) {
        try{
            List<String> list = Files.readAllLines(Paths.get("config.txt"));

            String ownerId = list.get(0);

            EventWaiter waiter = new EventWaiter();

            client = new CommandClientBuilder()
                    .setOwnerId(ownerId)
                    .setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26")
                    .setPrefix(Infos.PREFIX.getS())
                    .addCommands(
                            new SetStatusCmd(),
                            new PingCommand(),
                            new SearchRepo()
                    )
                    .setHelpWord("help")
                    .useHelpBuilder(true)
                    .setActivity(Activity.watching("Minemobs developping"))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB);

            jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
            jda.addEventListener(new Listener());
            jda.addEventListener(waiter, client.build());
        }catch (LoginException | IOException e){
            e.printStackTrace();
        }
    }
}
