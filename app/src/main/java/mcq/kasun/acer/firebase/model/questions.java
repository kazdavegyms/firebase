package mcq.kasun.acer.firebase.model;

/**
 * Created by acer on 4/13/2018.
 */

public class questions {

    private String Question,AnswerA,AnswerB,AnswerC,AnswerD,AnswerE,CorrectAnswer,CategoryId,IsImageQuestion,pno;

    public questions() {
    }

    public questions(String question, String answerA, String answerB, String answerC, String answerD, String answerE, String correctAnswer, String categoryId, String isimageQuestion,String Pno) {


        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        AnswerE = answerE;
        CorrectAnswer = correctAnswer;
        CategoryId = categoryId;
        IsImageQuestion = isimageQuestion;
        pno=Pno;

    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    //added by me

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getAnswerE() {
        return AnswerE;
    }

    public void setAnswerE(String answerE) {
        AnswerE = answerE;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getIsimageQuestion() {
        return IsImageQuestion;
    }

    public void setIsimageQuestion(String isimageQuestion) {
        IsImageQuestion = isimageQuestion;
    }
}
