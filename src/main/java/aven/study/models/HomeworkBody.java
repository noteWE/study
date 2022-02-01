package aven.study.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("HomeworkBodies")
@Getter
@Setter
public class HomeworkBody {
    @Id
    private Integer id;

    @Column("WrittenAnswer")
    private String writtenAnswer;

    @Column("FileAnswer")
    private String fileAnswer;
}
