package uenderley.com;

import javax.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "usuario")
public class User extends PanacheEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}