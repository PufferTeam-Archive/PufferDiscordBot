package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.internal.entities.UserImpl;

public class SuggestionCommand extends Command {
    final String minemobsID;

    public SuggestionCommand(){
        minemobsID = "363811352688721930";
        this.guildOnly = true;
        this.help = "Send a suggestion to minemobs";
        this.name = "suggestion";
        this.aliases = new String[]{"sugg"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if(!event.getAuthor().isBot()){
            String[] args = event.getMessage().getContentRaw().split(" ");
            if(!event.getJDA().getUserById(minemobsID).hasPrivateChannel())
                event.getJDA().getUserById(minemobsID).openPrivateChannel().complete();

            ((UserImpl)event.getJDA().getUserById(minemobsID))
                    .getPrivateChannel().sendMessage("Le joueur " + event.getAuthor().getAsMention()
                    + " a fait une suggestion ```" + event.getMessage().getContentRaw().replace(args[0], "") + "```").queue();

            event.getChannel().sendMessage( event.getAuthor().getAsMention() + " La suggestion à bien été envoyée").queue();
            event.getMessage().delete().queue();
        }
    }
}
