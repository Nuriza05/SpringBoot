package peaksoft.service;
import peaksoft.model.Department;

import java.util.List;


public interface DepartmentService {
    Department save(Long id,Department department);
    void deleteById(Long id);
    Department getById(Long id);
     void update (Long id, Department newDepartment);
    List<Department> getAll(Long id);

}