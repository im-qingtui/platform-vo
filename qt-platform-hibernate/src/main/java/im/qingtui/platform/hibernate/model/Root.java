package im.qingtui.platform.hibernate.model;

import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "root")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
public class Root extends im.qingtui.platform.hibernate.model.Entity {

    private static final long serialVersionUID = 1L;

    private String id;
    private long createTime;
    private long updateTime;


    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

}
