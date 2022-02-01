package aven.study.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("PersonCourses")
@Data
public class PersonCourse {
    @Id
    private Integer id;

    @Column("IdPerson")
    private Integer idPerson;
    @Column("IdCourse")
    private Integer idCourse;
    @Column("StartDate")
    private Date startDate;
    @Column("EndDate")
    private Date endDate;
    private Integer rate;
}
