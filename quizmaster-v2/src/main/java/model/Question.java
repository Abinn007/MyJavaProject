package model;
/**
 * @author Carolina Lira del Alto
 */
public class Question {
    private int idQuestion;
    private String textQuestion;
    Quiz quiz;
    // CONSTRUCTORS
    public Question(int idQuestion, String textQuestion, Quiz quiz) {
        this.idQuestion = idQuestion;
        this.textQuestion = textQuestion;
        this.quiz = quiz;
    }
    public Question(String textQuestion, Quiz quiz) {
        this.textQuestion = textQuestion;
        this.quiz = quiz;
    }
    public Question(int idQuestion, String textQuestion) {
        this.idQuestion = idQuestion;
        this.textQuestion = textQuestion;
    }
    // GETTERS & SETTERS
    public int getIdQuestion() {
        return idQuestion;
    }
    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }
    public String getTextQuestion() {
        return textQuestion;
    }
    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    // METHODS
    public String toString() {
        return getTextQuestion();
    }
}