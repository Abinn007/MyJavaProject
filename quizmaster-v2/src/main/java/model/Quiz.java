package model;

import java.util.Objects;

/**
 * @author lisa
 * class voor aanmaken van een Quiz object
 */

public class Quiz {
    private int idQuiz;
    private String naam;
    private int succesdefinitie;
    private Course course;

    public Quiz(int idQuiz, String naam, int succesdefinitie, Course course) {
        this.idQuiz = idQuiz;
        this.naam = naam;
        this.succesdefinitie = succesdefinitie;
        this.course = course;
    }

    public Quiz(String naam, int succesdefinitie, Course course) {
        this.naam = naam;
        this.succesdefinitie = succesdefinitie;
        this.course = course;
    }

    public Quiz() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getSuccesdefinitie() {
        return succesdefinitie;
    }

    public void setSuccesdefinitie(int succesdefinitie) {
        this.succesdefinitie = succesdefinitie;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nNaam: %s\nCesuur: %d\n",getIdQuiz(), getNaam(),getSuccesdefinitie());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getIdQuiz() == quiz.getIdQuiz() &&
                getSuccesdefinitie() == quiz.getSuccesdefinitie() &&
                getNaam().equals(quiz.getNaam()) &&
                getCourse().equals(quiz.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdQuiz(), getNaam(), getSuccesdefinitie(), getCourse());
    }
}
