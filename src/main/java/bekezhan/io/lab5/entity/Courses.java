package bekezhan.io.lab5.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;
}