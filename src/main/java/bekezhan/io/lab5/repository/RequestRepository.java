package bekezhan.io.lab5.repository;

import bekezhan.io.lab5.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}