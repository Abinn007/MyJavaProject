package model;

import java.util.Date;

/**
 * QuizResult houdt de resultaten bij van studenten die een quiz maken
 *
 * @author Wesley Wong
 */

public class QuizResult{

    private Date dt = new java.util.Date();
    private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String date = sdf.format(dt);

    private int idAttempt;
    private int idQuiz;
    private int idStudent;
    private Boolean passed;

    /**
     * Constructors
     */
    public QuizResult(int idAttempt, int idQuiz, int idStudent, Boolean passed, String date) {
        this.idAttempt = idAttempt;
        this.idQuiz = idQuiz;
        this.idStudent = idStudent;
        this.passed = passed;
        this.date = date;
    }

    public QuizResult(int idQuiz, int idStudent, Boolean passed) {
        this.idQuiz = idQuiz;
        this.idStudent = idStudent;
        this.passed = passed;


    }

    public QuizResult(int idQuiz, int idStudent) {
        this(idQuiz, idStudent, false);
    }

    /**
     * Getters & Setters
     */
    public int getIdQuiz() {
        return idQuiz;
    }

    public String getDate() {
        return date;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public int getIdAttempt() {
        return idAttempt;
    }

    /**
     * Methods
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Datum: ").append(getDate()).append("\n");
        if (getPassed()){
            sb.append("Geslaagd\n");
        } else{
            sb.append("Gezakt\n");
        }
        return sb.toString();
    }
}
