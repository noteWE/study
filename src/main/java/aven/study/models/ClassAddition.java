package aven.study.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ClassAdditions")
public class ClassAddition {
    @Id
    private Integer id;

    @Column("IdAddition")
    private Integer idAddition;

    @Column("IdClass")
    private Integer idClass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAddition() {
        return idAddition;
    }

    public void setIdAddition(Integer idAddition) {
        this.idAddition = idAddition;
    }

    public Integer getIdClass() {
        return idClass;
    }

    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }
}
