package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.model.Patient;

/**
 * @created : Lenovo Nuriza
 **/
public interface PatientRepo extends JpaRepository<Patient,Long> {
}
