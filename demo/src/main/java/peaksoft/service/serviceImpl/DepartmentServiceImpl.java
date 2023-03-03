package peaksoft.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepo;
import peaksoft.repository.DepartmentRepo;
import peaksoft.repository.HospitalRepo;
import peaksoft.service.DepartmentService;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final HospitalRepo hospitalRepo;
    private final DepartmentRepo departmentRepo;
    private final AppointmentRepo appointmentRepo;

    @Override
    public Department save(Long hospitalId, Department department){
        Hospital hospital = hospitalRepo.findById(hospitalId).get();
        department.setHospital(hospital);
        return departmentRepo.save(department);
    }

    @Override
    public void deleteById(Long id) {
        Department department = departmentRepo.findById(id).get();
        List<Appointment> appointments = department.getHospital().getAppointments();

        if (appointments != null) {
            List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
            appointmentList.forEach(s -> appointmentRepo.deleteById(s.getId()));
        }

        List<Department> departments = department.getHospital().getDepartments();
        departments.removeIf(s -> s.getId().equals(id));

        departmentRepo.deleteById(id);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepo.findById(id).get();
    }

    @Override
    public void update(Long id, Department newDepartment){
        Department department = departmentRepo.findById(id).get();
        department.setName(newDepartment.getName());
    }

    @Override
    public List<Department> getAll(Long id) {
        return hospitalRepo.findById(id).get().getDepartments();
    }
}
