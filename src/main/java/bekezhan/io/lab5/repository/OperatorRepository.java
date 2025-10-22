package bekezhan.io.lab5.repository;

import bekezhan.io.lab5.entity.Operators;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operators, Long> {
}