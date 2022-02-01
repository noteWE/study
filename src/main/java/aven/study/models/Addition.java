package aven.study.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Additions")
@Data
public class Addition {
    @Id
    private int id;

    private String description;
    private String link;
}
