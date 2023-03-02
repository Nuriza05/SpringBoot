package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.model.Department;

/**
 * @created : Lenovo Nuriza
 **/
public interface DepartmentRepo extends JpaRepository<Department,Long> {
}
