package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.minemobs.discordbot.PufferTeamMain.PufferTeamMain;
import net.dv8tion.jda.api.requests.RestAction;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

public class SearchRepo extends Command {

    GitHub github = null;

    public SearchRepo() {
        this.name = "searchrepo";
        this.aliases = new String[]{"srepo","github"};
        this.guildOnly = true;
        this.help = "Search a repo in github. \n Exemple : ```*searchrepo pufferTeam/PufferDiscordBot```";
        try {
            github = new GitHubBuilder().withOAuthToken(PufferTeamMain.githubToken).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getAuthor().isBot()) return;
        String[] cmdArgs = event.getArgs().split("/");
        if(getArguments().length() == 1 && cmdArgs.length == 2){
            GHRepositorySearchBuilder repo = github.searchRepositories().user(cmdArgs[0]).repo(cmdArgs[1]);
        }else{
            event.replyError("Hey this command need 1 argument");
        }
    }
}
