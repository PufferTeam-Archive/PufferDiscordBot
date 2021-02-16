package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.minemobs.discordbot.PufferTeamMain.Categories;
import fr.minemobs.discordbot.PufferTeamMain.PufferTeamMain;

public class PingCommand extends Command {

    public PingCommand(){
        this.name = "ping";
        this.help = "Get the ping of the bot and its API";
        this.guildOnly = false;
        this.category = Categories.getRandomCategory();
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getChannel().sendMessage("Discord : " + PufferTeamMain.getJda().getGatewayPing() + " ms").queue();
        event.getChannel().sendMessage("Api : " + PufferTeamMain.getJda().getRestPing().complete() + " ms").queue();
    }

}
