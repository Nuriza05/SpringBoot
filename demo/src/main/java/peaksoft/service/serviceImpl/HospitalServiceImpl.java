package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepo;
import peaksoft.service.HospitalService;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepo hospitalRepo;
    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    @Override
    public List<Hospital> getAll(String keyWord) {
        if(keyWord !=  null && !keyWord.trim().isEmpty()) {
            return
                    hospitalRepo.searchByName("%"+keyWord+"%");
        }else {
            return hospitalRepo.findAll();
        }
    }

    @Override
    public List<Hospital> getAll() {
        return hospitalRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
       hospitalRepo.deleteById(id);
    }

    @Override
    public Hospital getById(Long id) {
        return hospitalRepo.findById(id).get();
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        Hospital hospital = hospitalRepo.findById(id).get();
        hospital.setName(newHospital.getName());
        hospital.setAddress(newHospital.getAddress());
        hospital.setAppointments(newHospital.getAppointments());
        hospital.setDoctors(newHospital.getDoctors());
        hospital.setPatients(newHospital.getPatients());
        hospital.setImageLink(newHospital.getImageLink());
        hospitalRepo.save(hospital);
    }
}
