package peaksoft.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepo;
import peaksoft.repository.DepartmentRepo;
import peaksoft.repository.DoctorRepo;
import peaksoft.repository.HospitalRepo;
import peaksoft.service.DoctorService;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;
    private final DepartmentRepo departmentRepo;
    private final AppointmentRepo appointmentRepo;
    @Override
    public Doctor save(Long id, Doctor newDoctor) {
        Hospital hospital = hospitalRepo.findById(id).get();
        Doctor doctor = new Doctor();
        doctor.setId(newDoctor.getId());
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setEmail(newDoctor.getEmail());
        doctor.setPosition(newDoctor.getPosition());
        doctor.setImageLink(newDoctor.getImageLink());
        doctor.setHospital(hospital);
        return doctorRepo.save(doctor);
    }

    @Override
    public List<Doctor> getAll(Long hospitalId) {
        return hospitalRepo.findById(hospitalId).get().getDoctors();
    }

    @Override
    public void deleteById(Long id) {
        Doctor doctor = doctorRepo.findById(id).get();
        List<Appointment> appointments = doctor.getAppointments();
        if (appointments != null) {
            List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDoctor().getId().equals(id)).toList();
            appointmentList.forEach(s -> appointmentRepo.deleteById(s.getId()));
        }
        List<Doctor> doctors = doctor.getHospital().getDoctors();
        doctors.removeIf(d -> d.getId().equals(id));
        doctorRepo.deleteById(id);
    }

    @Override
    public Doctor getById(Long id) {
        return doctorRepo.findById(id).get();
    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        Doctor doctor = doctorRepo.findById(id).get();
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setPosition(newDoctor.getPosition());
        doctor.setEmail(newDoctor.getEmail());
    }

    @Override
    public void assignDepsToDoctor(Long docId, List<Long> depsId) {
        Doctor doctor = doctorRepo.findById(docId).orElseThrow(() -> new IllegalArgumentException("Invalid doctor id: " + docId));
        List<Department> departments = departmentRepo.findAllById(depsId);
            for (Department department : departments) {
                doctor.addDepartment(department);
                department.addDoctor(doctor);
            }
            doctorRepo.save(doctor);
        }
    }

