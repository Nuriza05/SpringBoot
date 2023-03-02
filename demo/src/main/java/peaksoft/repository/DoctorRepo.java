package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface DoctorRepo extends JpaRepository<Doctor,Long> {

//     void assignDepsToDoctor(Long docId, List<Long> depsId);


}
