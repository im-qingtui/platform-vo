package entity;

import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;

/**
 * Created by sunny on 16/7/26.
 */
@Entity
@Table(name = "rootbean")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
public class RootBean extends im.qingtui.platform.hibernate.model.Root {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
