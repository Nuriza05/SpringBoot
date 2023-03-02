package peaksoft.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.MyException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.repository.*;
import peaksoft.service.AppointmentService;
import java.time.LocalDate;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final HospitalRepo hospitalRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final DepartmentRepo departmentRepo;

    @Override
    public Appointment save(Long id, Appointment appointment) throws MyException {
        Hospital hospital = hospitalRepo.findById(id).get();
        Appointment appointment1 = new Appointment();
        appointment1.setId(appointment.getId());
        appointment1.setPatient(patientRepo.findById(appointment.getPatientId()).get());
        appointment1.setDoctor(doctorRepo.findById(appointment.getDoctorId()).get());
        appointment1.setDepartment(departmentRepo.findById(appointment.getDepartmentId()).get());
        appointment1.setDate(appointment.getDate());
        hospital.addAppoint(appointment1);
        if (appointment1.getDate().isAfter(LocalDate.now())) {
            return appointmentRepo.save(appointment1);
        } else {
            throw new MyException("Date should be in future time!");
        }
    }

    @Override
    public List<Appointment> getAll(Long id) {
        return hospitalRepo.findById(id).get().getAppointments();
    }

    @Override
    public void deleteById(Long hId, Long id) {
        List<Hospital> hospitals = hospitalRepo.findAll();
        hospitals.forEach(h-> h.getAppointments().removeIf(a->a.getId().equals(id)));
        appointmentRepo.deleteById(id);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepo.findById(id).get();
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        Appointment appointment = appointmentRepo.findById(id).get();
        appointment.setDate(newAppointment.getDate());
        appointment.setPatient(patientRepo.findById(newAppointment.getPatientId()).get());
        appointment.setDoctor(doctorRepo.findById(newAppointment.getDoctorId()).get());
        appointment.setDepartment(departmentRepo.findById(newAppointment.getDepartmentId()).get());
    }
}
