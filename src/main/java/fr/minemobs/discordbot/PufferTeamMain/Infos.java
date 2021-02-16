package fr.minemobs.discordbot.PufferTeamMain;

public enum Infos {
    PREFIX("%");

    String s;
    Infos(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
