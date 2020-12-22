package fr.minemobs.discordbot.PufferTeamMain;

import fr.minemobs.discordbot.PufferTeamMain.listener.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class PufferTeamMain {

    public static Logger LOGGER = LoggerFactory.getLogger(PufferTeamMain.class);
    public static JDA jda;

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
            jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
            jda.addEventListener(new Listener());
        }catch (LoginException e){
            e.printStackTrace();
        }
    }
}
