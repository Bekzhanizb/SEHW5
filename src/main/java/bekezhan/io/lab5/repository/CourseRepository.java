package bekezhan.io.lab5.repository;

import bekezhan.io.lab5.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Long> {
}