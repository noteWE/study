package aven.study.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("CourseTeachers")
@Data
public class CourseTeacher {
    @Id
    private Integer id;

    @Column("IdCourse")
    private Integer idCourse;
    @Column("IdPerson")
    private Integer idPerson;
}
