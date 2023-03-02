package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.enums.Gender;
import peaksoft.exceptions.MyException;
import peaksoft.model.Patient;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientApi {
    private final PatientService patientService;
    private final HospitalService hospitalService;

    @Autowired
    public PatientApi(PatientService patientService, HospitalService hospitalService) {
        this.patientService = patientService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("/{hospitalId}")
    public String findAll(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("patients", patientService.getAll(hospitalId));
        return "patient/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newPatient", new Patient());
        model.addAttribute(hospitalId);
        model.addAttribute("genders", Gender.values());
        return "patient/savePage";
    }

    @PostMapping("/{hospitalId}/savePage")
    public String save(@PathVariable Long hospitalId,
                       @ModelAttribute("newPatient")@Valid Patient patient,
                       BindingResult bindingResult, Model model) throws MyException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            return "patient/savePage";
        }
        try {
            patientService.save(hospitalId, patient);
            return "redirect:/patients/" + hospitalId;
        } catch (MyException e){
            model.addAttribute("phoneNumber",e.getMessage());
            model.addAttribute("genders", Gender.values());
            return "patient/savePage";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("Email", "This email is already exists in database!");
            return "patient/savePage";
        }
    }

    @GetMapping("/{hospitalId}/{patientId}/delete")
    public String deleteById(@PathVariable Long hospitalId,@PathVariable Long patientId) {
        patientService.deleteById(patientId);
        return "redirect:/patients/"+hospitalId;
    }
    @GetMapping("/{hospitalId}/{patientId}/edit")
    public String edit(Model model, @PathVariable Long patientId, @PathVariable Long hospitalId) {
        model.addAttribute("patient", patientService.getById(patientId));
        model.addAttribute(hospitalId);
        model.addAttribute("genders",Gender.values());
        return "patient/update";
    }

    @PostMapping("/{hospitalId}/{patientId}/update")
    public String update(@PathVariable Long hospitalId,
                         @PathVariable Long patientId,
                         @ModelAttribute("patient") Patient patient) throws MyException {
        patientService.update(patientId, patient);
        return "redirect:/patients/"+hospitalId;
    }
}
