package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.MyException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.model.Patient;
import peaksoft.repository.AppointmentRepo;
import peaksoft.repository.HospitalRepo;
import peaksoft.repository.PatientRepo;
import peaksoft.service.PatientService;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final HospitalRepo hospitalRepo;
    private final AppointmentRepo appointmentRepo;
    @Override
    public Patient save(Long id, Patient patient) throws MyException {
        if(patient.getPhoneNumber().startsWith("+996") && patient.getPhoneNumber().chars().count()==13) {
            Hospital hospital = hospitalRepo.findById(id).get();
            patient.setHospital(hospital);
            return patientRepo.save(patient);
        }else {
            throw new MyException("Phone number should be starts with +996 and size = 13!");
        }

    }

    @Override
    public List<Patient> getAll(Long id) {
        return hospitalRepo.findById(id).get().getPatients();
    }

    @Override
    public void deleteById(Long id) {
        Patient patient = patientRepo.findById(id).get();
        List<Appointment> appointments = patient.getAppoitmentList();
        if (appointments != null) {
            List<Appointment> appointmentList = appointments.stream().filter(s -> s.getPatient().getId().equals(id)).toList();
            appointmentList.forEach(s -> appointmentRepo.deleteById(s.getId()));
        }
        List<Patient> patients = patient.getHospital().getPatients();
        patients.removeIf(s -> s.getId().equals(id));

        patientRepo.deleteById(id);

    }

    @Override
    public Patient getById(Long id) {
        return patientRepo.findById(id).get();
    }

    @Override
    public void update(Long id, Patient newPatient) throws MyException {
        if(newPatient.getPhoneNumber().startsWith("+996") && newPatient.getPhoneNumber().chars().count()==13) {
            Patient patient = patientRepo.findById(id).get();
            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setPhoneNumber(newPatient.getPhoneNumber());
            patient.setGender(newPatient.getGender());
            patient.setEmail(newPatient.getEmail());
            patient.setAppoitmentList(newPatient.getAppoitmentList());
        }else {
            throw new MyException("Phone number should be starts with +996 and size == 13!");
        }
    }
}
