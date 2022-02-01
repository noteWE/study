package aven.study.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("Homeworks")
@Data
public class Homework {
    @Id
    private Integer id;

    @Column("IdPerson")
    private Integer idPerson;

    @Column("IdClass")
    private Integer idClass;

    @Column("IdHomeworkBody")
    private Integer idHomeworkBody;

    @Column("DateOfCompletion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfCompletion;

    private Integer rate;
}
