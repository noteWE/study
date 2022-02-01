package aven.study.models.dto;

import aven.study.models.CourseState;
import aven.study.models.CourseTheme;
import aven.study.models.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("RatedCourses")
@Data
public class RatedCourse {
    private Integer id;
    @JsonView(Views.DataWithoutKeys.class)
    private String title;
    @JsonView(Views.DataWithoutKeys.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CourseTheme theme;
    @JsonView(Views.DataWithoutKeys.class)
    private int difficult;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CourseState state;
    @Column("IdPerson")
    private Integer idTeacher;
    @Column("AverageRate")
    private Double averageRate;
}
