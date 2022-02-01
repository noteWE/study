package aven.study.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("Persons")
@Data
public class Person {
    @Id
    @JsonView(Views.PublicData.class)
    private Integer id;

    @Column("Firstname")
    @JsonView(Views.PublicData.class)
    private String firstName;
    @Column("Lastname")
    @JsonView(Views.PublicData.class)
    private String lastName;
    @JsonView(Views.PublicData.class)
    private String gender;
    @Column("Birthdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonView(Views.PublicData.class)
    private Date birthDate;
    @JsonView(Views.PublicData.class)
    private String email;
    @JsonView(Views.PublicData.class)
    private String phone;
    @JsonView(Views.PublicData.class)
    private String login;
    @JsonView(Views.Password.class)
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonView(Views.PublicData.class)
    private PersonRoles role;
}
