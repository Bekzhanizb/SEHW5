package bekezhan.io.lab5.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToMany
    @JoinTable(
            name = "request_operators",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    private Set<Operators> operators = new HashSet<>();
}