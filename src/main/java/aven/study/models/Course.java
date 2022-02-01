package aven.study.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("Courses")
@Data
public class Course {
    @Id
    @JsonView(Views.Fields.class)
    private Integer id;

    @JsonView(Views.DataWithoutKeys.class)
    private String title;
    @JsonView(Views.DataWithoutKeys.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CourseTheme theme;
    @JsonView(Views.DataWithoutKeys.class)
    private int difficult;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonView(Views.Fields.class)
    private CourseState state;
}
