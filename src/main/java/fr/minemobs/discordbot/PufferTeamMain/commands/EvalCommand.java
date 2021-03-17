package fr.minemobs.discordbot.PufferTeamMain.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.Permissions;
import java.util.Collections;
import java.util.List;

public class EvalCommand extends Command {

    private ScriptEngine engine;

    public EvalCommand()
    {
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.cooldown = 5;
        this.guildOnly = true;
        this.name = "Evaluate";
        this.help = "Takes Java or JavaScript code and executes it. \n " +
                "Instructions: " +
                "*eval <Java code>\n" +
                "    Example: `.eval return \"5 + 5 is: \" + (5 + 5);\n" +
                "    This will print: 5 + 5 is: 10";
        this.aliases = new String[]{"eval"};

        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try
        {
            engine.eval("var imports = new JavaImporter(" +
                    "java.io," +
                    "java.lang," +
                    "java.util," +
                    "Packages.net.dv8tion.jda.api," +
                    "Packages.net.dv8tion.jda.api.entities," +
                    "Packages.net.dv8tion.jda.api.entities.impl," +
                    "Packages.net.dv8tion.jda.api.managers," +
                    "Packages.net.dv8tion.jda.api.managers.impl," +
                    "Packages.net.dv8tion.jda.api.utils);");
        }
        catch (ScriptException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void execute(CommandEvent e) {
        if(e.getAuthor().isBot()) return;
        String[] args = e.getArgs().split(" ");

        try
        {
            engine.put("event", e);
            engine.put("message", e.getMessage());
            engine.put("channel", e.getChannel());
            engine.put("args", args);
            engine.put("api", e.getJDA());
            if (e.isFromType(ChannelType.TEXT))
            {
                engine.put("guild", e.getGuild());
                engine.put("member", e.getMember());
            }

            Object out = engine.eval(
                    "(function() {" +
                            "with (imports) {" +
                            e.getMessage().getContentDisplay().substring(args[0].length()) +
                            "}" +
                            "})();");
            e.reply(out == null ? "Executed without error." : out.toString());
        }
        catch (Exception ex)
        {
            e.reply(ex.getMessage());
        }
    }
}
