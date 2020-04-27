package model;

/**
 *
 * @author Wesley Wong
 */

public class Role {

    int idRole;
    String nameRole;

    public Role(int idRole, String nameRole) {
        this.idRole = idRole;
        this.nameRole = nameRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    @Override
    public String toString(){
        return nameRole;
    }

}

