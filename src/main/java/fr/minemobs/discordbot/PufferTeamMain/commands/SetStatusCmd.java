package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.minemobs.discordbot.PufferTeamMain.Categories;
import fr.minemobs.discordbot.PufferTeamMain.PufferTeamMain;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;

public class SetStatusCmd extends Command {

    public SetStatusCmd() {
        this.name = "setStatus";
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.arguments = "<StatusType> <Name of the activity>";
        this.cooldown = 15;
        this.guildOnly = true;
        this.help = "Set a status for the bot";
        this.category = Categories.getStaffCategory();
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty()){
            event.replyWarning("You need to give me 2 args! \n" +
                    "Like **watching minemobs eating paella**");
        }else{
            String[] args = event.getArgs().split("\\s+");
            String argsExceptTheFirstOne = event.getArgs().substring(args[0].length());

            Presence presence = PufferTeamMain.getJda().getPresence();

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
                        case "competing":
                            presence.setActivity(Activity.competing(argsExceptTheFirstOne));
                            event.replySuccess("Ok now the activity is " + argsExceptTheFirstOne);
                        default:
                            event.replyWarning("Please set in the first args **watching** or **listening** or **playing** or **competing**");
                            break;
                    }
                }
            }
        }
    }
}
