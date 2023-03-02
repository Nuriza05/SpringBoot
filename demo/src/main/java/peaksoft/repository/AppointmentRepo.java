package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.model.Appointment;

/**
 * @created : Lenovo Nuriza
 **/
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
}
