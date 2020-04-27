package model;

import controller.SessionController;

import java.util.Objects;

/**
 * @author lisa kemeling
 * class maakt een Course object
 */

public class Course {
    private int idCourse;
    private String nameCourse;
    private User user;

    public Course(int idCourse, String nameCourse, User user) {
        this.idCourse = idCourse;
        this.nameCourse = nameCourse;
        this.user = user;
    }

    public Course(String nameCourse, User user) {
        this.nameCourse = nameCourse;
        this.user = user;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return wanneer huidige user een coordinator is, dan toont toString niet de naam van de coordinator
     */

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int idRol = SessionController.currentUser.getIdRol();
        stringBuilder.append(String.format("ID: %d\nNaam: %s\n", getIdCourse(), getNameCourse()));
        if (!SessionController.currentUser.getNameRolById(idRol).equals("coordinator")) {
            stringBuilder.append("Coordinator: ").append(getUser().getGebruikersnaam());
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getIdCourse() == course.getIdCourse() &&
                getNameCourse().equals(course.getNameCourse()) &&
                getUser().equals(course.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCourse(), getNameCourse(), getUser());
    }
}
