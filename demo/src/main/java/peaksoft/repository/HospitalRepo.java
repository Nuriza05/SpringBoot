package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.model.Hospital;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface HospitalRepo extends JpaRepository<Hospital,Long> {
    List<Hospital> searchByName(String keyWord);
}
