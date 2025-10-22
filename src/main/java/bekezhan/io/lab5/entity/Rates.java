package bekezhan.io.lab5.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    private int rating;
    private String comment;
}