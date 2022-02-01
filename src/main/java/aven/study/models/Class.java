package aven.study.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("Classes")
public class Class {
    @Id
    private Integer id;

    @Column("IdCourse")
    private Integer idCourse;

    @JsonView(Views.DataWithoutKeys.class)
    private String title;

    @Column("IdClassBody")
    private Integer idClassBody;

    @JsonView(Views.DataWithoutKeys.class)
    @Column("WrittenAnswer")
    private boolean writtenAnswer;

    @JsonView(Views.DataWithoutKeys.class)
    @Column("FileAnswer")
    private boolean fileAnswer;

    @JsonView(Views.DataWithoutKeys.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIdClassBody() {
        return idClassBody;
    }

    public void setIdClassBody(Integer idClassBody) {
        this.idClassBody = idClassBody;
    }

    public boolean isWrittenAnswer() {
        return writtenAnswer;
    }

    public void setWrittenAnswer(boolean writtenAnswer) {
        this.writtenAnswer = writtenAnswer;
    }

    public boolean isFileAnswer() {
        return fileAnswer;
    }

    public void setFileAnswer(boolean fileAnswer) {
        this.fileAnswer = fileAnswer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
