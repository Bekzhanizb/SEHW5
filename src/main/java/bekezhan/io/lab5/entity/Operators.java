package bekezhan.io.lab5.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Operators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String department;
}