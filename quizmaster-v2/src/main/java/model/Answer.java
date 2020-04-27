package model;
/**
 * @author Carolina Lira del Alto
 */
public class Answer {
    private int idAnswer;
    private String textAnswer;
    private boolean isCorrect;
    Question question;
    // CONSTRUCTORS
    public Answer(int idAnswer, String textAnswer, boolean isCorrect, Question question) {
        this.idAnswer = idAnswer;
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
        this.question = question;
    }
    public Answer(String textAnswer, boolean isCorrect, Question question) {
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
        this.question = question;
    }
    public Answer(int idAnswer, String textAnswer, boolean isCorrect) {
        this.idAnswer = idAnswer;
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
    }
    // GETTERS & SETTERS
    public int getIdAnswer() {
        return idAnswer;
    }
    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }
    public String getTextAnswer() {
        return textAnswer;
    }
    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }
    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    // METHODS
    public String toString() { return getTextAnswer(); }
}
