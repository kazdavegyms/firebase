package mcq.kasun.acer.firebase.model;

/**
 * Created by acer on 6/10/2018.
 */

public class questionScore {

    private  String Question_score;
    private  String User;
    private  String score;
    private  String Categoryid;
    private  String CategoryName;
    private  String attempt;


    public questionScore() {
    }

    public questionScore(String question_score, String user, String score, String categoryid, String categoryName, String attempt) {
        Question_score = question_score;
        this.User = user;
        this.score = score;
        Categoryid = categoryid;
        CategoryName = categoryName;
        this.attempt = attempt;
    }

    public String getQuestion_score() {
        return Question_score;
    }

    public void setQuestion_score(String question_score) {
        Question_score = question_score;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }
}
