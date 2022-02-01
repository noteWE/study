package aven.study.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class ClassDto {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    private boolean writtenAnswer;
    private boolean fileAnswer;
    private String classDescription;
    private String homeworkDescription;
}
