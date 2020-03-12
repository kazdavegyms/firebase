package mcq.kasun.acer.firebase.model;

/**
 * Created by acer on 6/26/2018.
 */

public class Rankingg {

    private String Username;
    private long score;

    public Rankingg() {
    }

    public Rankingg(String username, long score) {
        Username = username;
        this.score = score;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}