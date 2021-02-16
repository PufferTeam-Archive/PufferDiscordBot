package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.minemobs.discordbot.PufferTeamMain.PufferTeamMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class SearchRepo extends Command {

    public SearchRepo() {
        this.name = "searchrepo";
        this.aliases = new String[]{"srepo","github"};
        this.guildOnly = true;
        this.help = "Search a repo in github. \n Exemple : ```*searchrepo pufferTeam/PufferDiscordBot```";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(CommandEvent event) {
        if(event.getAuthor().isBot()) return;
        String[] args = event.getArgs().split(" ");
        if(args.length == 1) {
            try {
                JSONParser parser = new JSONParser();
                URL url = new URL(String.format("https://api.github.com/repos/%s", args[0]));
                InputStreamReader reader = new InputStreamReader(url.openStream());

                Object jsonObj = null;
                try {
                    jsonObj = parser.parse(reader);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = (JSONObject) jsonObj;
                GithubRndClass githubRndClass = null;

                try{
                    githubRndClass = new GithubRndClass((String) Objects.requireNonNull(jsonObject.get("name")),
                            (String) Objects.requireNonNull(jsonObject.get("html_url")), (String) Objects.requireNonNull(jsonObject.get("created_at")),
                            (String) Objects.requireNonNull(jsonObject.get("updated_at")), (String) Objects.requireNonNull(jsonObject.get("git_url")),
                            (HashMap<String, Object>) Objects.requireNonNull(jsonObject.get("owner")), (String) Objects.requireNonNull(jsonObject.get("language")));

                    MessageEmbed eb = new EmbedBuilder()
                            .setAuthor(githubRndClass.getOwner().get("login").toString(), githubRndClass.getOwner().get("html_url").toString(),
                                    githubRndClass.getOwner().get("avatar_url").toString())
                            .setImage(githubRndClass.getOwner().get("avatar_url").toString())
                            .addField("Repository",String.format("[%s](%s)",githubRndClass.name, githubRndClass.html_url), true)
                            .addField("Most Used Language", githubRndClass.getLanguage(), true)
                            .setTimestamp(githubRndClass.created_at_date)
                            .setColor(new Color(26, 188, 156))
                            .build();

                    event.getChannel().sendMessage(eb).mentionRepliedUser(true).queue();
                }catch (NullPointerException ex){
                    event.replyError("Oh no there is an error. Please send a message to minemobs#6904");
                }
            } catch (FileNotFoundException e){
                event.replyError("Hey, this url is invalid!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            event.replyError("Hey this command need 1 argument");
            System.out.println(event.getArgs().length());
        }
    }

    private class GithubRndClass {
        private String name;
        private String html_url;
        private String created_at;
        private String updated_at;
        private String git_url;
        private String language;
        private HashMap<String, Object> owner;

        private LocalDateTime created_at_date;
        private LocalDateTime updated_at_date;

        public GithubRndClass(String name, String html_url, String created_at, String updated_at, String git_url, HashMap<String, Object> owner, String language) {
            this.name = name;
            this.html_url = html_url;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.git_url = git_url;
            this.owner = owner;
            this.language = language;

            created_at_date = LocalDateTime.parse(created_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            updated_at_date = LocalDateTime.parse(created_at.replaceAll("Z",""), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        }

        public String getName() {
            return name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getGit_url() {
            return git_url;
        }

        public String getHtml_url() {
            return html_url;
        }

        public HashMap<String, Object> getOwner() {
            return owner;
        }

        public LocalDateTime getCreated_at_date() {
            return created_at_date;
        }

        public LocalDateTime getUpdated_at_date() {
            return updated_at_date;
        }

        public String getLanguage() {
            return language;
        }

        @Override
        public String toString() {
            return "GithubRndClass{" +
                    "name='" + name + '\'' +
                    ", html_url='" + html_url + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", git_url='" + git_url + '\'' +
                    ", language='" + language + '\'' +
                    ", owner=" + owner +
                    ", created_at_date=" + created_at_date +
                    ", updated_at_date=" + updated_at_date +
                    '}';
        }
    }
}
