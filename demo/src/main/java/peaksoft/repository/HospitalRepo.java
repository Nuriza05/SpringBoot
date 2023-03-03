package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Hospital;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface HospitalRepo extends JpaRepository<Hospital,Long> {
    @Query("select l from Hospital l where l.name ilike (:keyWord)")
    List<Hospital> searchByName(String keyWord);
}
