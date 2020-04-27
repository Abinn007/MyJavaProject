package model;


import java.util.Objects;

/**
 * Deze class is group van een aantal studenten die een bepaalde cursus volgt.
 * @author HRGautam
 */

public class Group {
    private int idGroup;
    private String nameGroup;
    private User user;
    private Course course;

    //constructors

    public Group(int idGroup, String nameGroup, Course course,User user) {
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.user = user;
        this.course = course;
    }
    public Group(String nameGroup, Course course,User user) {
        this.nameGroup = nameGroup;
        this.user = user;
        this.course = course;
    }


    //Getters en Setters

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString(){
       return String.format("ID: %d\nNaam: %s\nCursus: %s\nDocent: %s\n",idGroup,nameGroup,getCourse().getNameCourse(),getUser().getGebruikersnaam());
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return getIdGroup() == group.getIdGroup() &&
                getNameGroup().equals(group.getNameGroup()) &&
                getUser().equals(group.getUser()) &&
                getCourse().equals(group.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdGroup(), getNameGroup(), getUser(), getCourse());
    }
}
