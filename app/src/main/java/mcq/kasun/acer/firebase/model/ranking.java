package mcq.kasun.acer.firebase.model;

/**
 * Created by acer on 6/26/2018.
 */

public class ranking {

    private  String Username;
    private  int sum;

    public ranking(String username, int sum) {
        Username = username;
        this.sum = sum;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
