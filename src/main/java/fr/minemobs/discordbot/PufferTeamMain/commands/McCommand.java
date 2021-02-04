package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.IOException;

public class McCommand extends Command {
    Rcon rcon = null;

    public McCommand(String password, String ip) {
        try {
            rcon = new Rcon(ip, 25565, password.getBytes());
        } catch (IOException | AuthenticationException e) {
            e.printStackTrace();
        }
        this.ownerCommand = true;
        this.guildOnly = true;
        this.hidden = true;
        this.name = "executecommand";
        this.aliases = new String[]{"ecommand","mcc"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getAuthor().isBot()) return;
        String command = event.getArgs();
        String result = "";
        try {
            result = rcon.command(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.isEmpty()){
            event.replySuccess("La commande à bien été exécuté.");
        }else{
            event.replySuccess(result);
        }
    }
}
