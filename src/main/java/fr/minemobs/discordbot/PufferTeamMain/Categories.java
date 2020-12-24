package fr.minemobs.discordbot.PufferTeamMain;

import com.jagrosh.jdautilities.command.Command;
import fr.minemobs.discordbot.PufferTeamMain.commands.PingCommand;

public class Categories {

    static Category staffCategory = new Category("Staff commands");

    static Category randomCategory = new Category("Other commands");

    public static Category getStaffCategory() {
        return staffCategory;
    }

    public static Category getRandomCategory() {
        return randomCategory;
    }

    public static class Category extends Command.Category {

        public Category(String name) {
            super(name);
        }
    }
}
