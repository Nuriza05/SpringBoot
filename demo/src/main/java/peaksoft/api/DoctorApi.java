package peaksoft.api;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorApi {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @Autowired
    public DoctorApi(DoctorService doctorService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }



    @GetMapping("/{hospitalId}")
    public String findAll(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("doctors", doctorService.getAll(hospitalId));
        return "doctor/mainPage";
    }


    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newDoctor", new Doctor());
        model.addAttribute(hospitalId);
        return "doctor/savePage";
    }


    @PostMapping("/{hospitalId}/savePage")
    public String save(@PathVariable Long hospitalId,
                       @ModelAttribute("newDoctor")@Valid Doctor doctor,
                       BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "doctor/savePage";
        }
        try{
            doctorService.save(hospitalId, doctor);
            return "redirect:/doctors/" + hospitalId;
        }catch (DataIntegrityViolationException e){
            model.addAttribute("Email","This email is already exists in database!");
            return "doctor/savePage";
        }

    }


    @GetMapping("/{hospitalId}/{doctorId}/assign-departments")
    public String showAssignDoctorsForm(@PathVariable Long hospitalId,
                                        @PathVariable Long doctorId, Model model){
        Doctor doctor = doctorService.getById(doctorId);
        List<Department> departments = departmentService.getAll(hospitalId);
        model.addAttribute("doctor",doctor);
        model.addAttribute("departments",departments);
        model.addAttribute(hospitalId);
        return "doctor/assign-departments";
    }


    @PostMapping("/{hospitalId}/{id}/save")
    public String assignDepsToDoctor( @PathVariable Long hospitalId,
                                      @PathVariable Long id,
                                      @RequestParam List<Long> departmentsId){
        doctorService.assignDepsToDoctor(id,departmentsId);
        return "redirect:/doctors/"+hospitalId;
    }


    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String deleteById(@PathVariable Long hospitalId,
                             @PathVariable Long doctorId) {
        doctorService.deleteById(doctorId);
        return "redirect:/doctors/" + hospitalId;
    }



    @GetMapping("/{hospitalId}/{doctorId}/edit")
    public String edit(Model model, @PathVariable Long doctorId,
                       @PathVariable Long hospitalId) {
        model.addAttribute("doctor",doctorService.getById(doctorId));
        model.addAttribute("hospitalId", hospitalId);
        return "doctor/update";
    }



    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String update(@PathVariable Long hospitalId,
                         @PathVariable Long doctorId,
                         @ModelAttribute("doctor")@Valid Doctor doctor,
    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "doctor/update";
        }
        try {
            doctorService.update(doctorId, doctor);
            return "redirect:/doctors/" + hospitalId;
        }catch (DataIntegrityViolationException e){
            model.addAttribute("Email","This email is already exists in database!");
            return "doctor/update";
        }
    }



}