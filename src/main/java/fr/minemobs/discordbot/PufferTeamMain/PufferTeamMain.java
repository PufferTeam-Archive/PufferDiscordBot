package fr.minemobs.discordbot.PufferTeamMain;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class PufferTeamMain {

    public static Logger LOGGER = LoggerFactory.getLogger(PufferTeamMain.class);

    public static void main(String[] args) {
        try{
            if(args[0].toLowerCase().contains("DISCORD_TOKEN=".toLowerCase())){
                launchDiscordBot(args[0]);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            LOGGER.error("Veuillez relancer le bot avec l'arguement : DISCORD_TOKEN=");
        }
    }

    private static void launchDiscordBot(String token) {
        try{
            JDA jda = JDABuilder.createDefault(token).build();
        }catch (LoginException e){
            e.printStackTrace();
        }
    }
}
