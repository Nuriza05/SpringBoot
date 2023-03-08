package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Department;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface DepartmentRepo extends JpaRepository<Department,Long> {
    @Query("select l from Department l join l.hospital h where h.id=:id ")
    List<Department> getAllDepartments(Long id);
}
