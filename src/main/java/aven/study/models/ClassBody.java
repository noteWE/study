package aven.study.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ClassBodies")
@Data
public class ClassBody {
    @Id
    private int id;

    @Column("ClassDescription")
    private String classDescription;

    @Column("HomeworkDescription")
    private String homeworkDescription;
}
