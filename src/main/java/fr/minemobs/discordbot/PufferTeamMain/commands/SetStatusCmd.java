package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.minemobs.discordbot.PufferTeamMain.Infos;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;

import static fr.minemobs.discordbot.PufferTeamMain.PufferTeamMain.client;
import static fr.minemobs.discordbot.PufferTeamMain.PufferTeamMain.jda;

public class SetStatusCmd extends Command {

    public SetStatusCmd() {
        this.name = "setStatus";
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.arguments = "<StatusType> <Name of the thing>";
        this.guildOnly = true;
        this.help = "Set a status for the bot";
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty()){
            event.replyWarning("You need to give me 2 args! \n" +
                    "Like **watching minemobs eating paella**");
        }else{
            String[] args = event.getArgs().split("\\s+");
            String argsExceptTheFirstOne = event.getArgs().substring(args[0].length());

            Presence presence = jda.getPresence();

            if(args[0].equalsIgnoreCase("reset")){
                presence.setActivity(Activity.watching("Minemobs developping"));
            }else{
                if(args.length < 2){
                    event.replyWarning("Please give me more than 1 arg");
                }else{
                    switch (args[0]){
                        case "watching":
                            presence.setActivity(Activity.watching(argsExceptTheFirstOne));
                            event.replySuccess("Ok now the activity is " + argsExceptTheFirstOne);
                            break;
                        case "listening":
                            presence.setActivity(Activity.listening(argsExceptTheFirstOne));
                            event.replySuccess("Ok now the activity is " + argsExceptTheFirstOne);
                            break;
                        case "playing":
                            presence.setActivity(Activity.playing(argsExceptTheFirstOne));
                            event.replySuccess("Ok now the activity is " + argsExceptTheFirstOne);
                            break;
                        default:
                            event.replyWarning("Please set in the first args **watching** or **listening** or **playing**");
                            break;
                    }
                }
            }
        }
    }
}
