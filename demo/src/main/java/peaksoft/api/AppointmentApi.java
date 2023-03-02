package peaksoft.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.exceptions.MyException;
import peaksoft.model.Appointment;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.PatientService;


@Controller
@RequestMapping("/appointments")
public class AppointmentApi {
    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    @Autowired
    public AppointmentApi(AppointmentService appointmentService,
                          DepartmentService departmentService,
                          DoctorService doctorService,
                          PatientService patientService) {
        this.appointmentService = appointmentService;
        this.departmentService = departmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("/{hospitalId}")
    public String findAll(@PathVariable Long hospitalId, Model model){
        model.addAttribute("appointments", appointmentService.getAll(hospitalId));
        return "appointment/mainPage";
    }



    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("departments",departmentService.getAll(hospitalId));
        model.addAttribute("patients",patientService.getAll(hospitalId));
        model.addAttribute("doctors",doctorService.getAll(hospitalId));
        model.addAttribute(hospitalId);
        return "appointment/savePage";
    }

    @PostMapping("/{hospitalId}/savePage")
    public String save(@PathVariable Long hospitalId,
                       @ModelAttribute("newAppointment")
                       Appointment appointment) throws MyException {
        appointmentService.save(hospitalId,appointment);
        return "redirect:/appointments/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{appointmentId}/edit")
    public String edit(Model model,@PathVariable Long hospitalId,
                       @PathVariable Long appointmentId) {
        model.addAttribute("appointment",appointmentService.getById(appointmentId));
        model.addAttribute("departments",departmentService.getAll(hospitalId));
        model.addAttribute("doctors",doctorService.getAll(hospitalId));
        model.addAttribute("patients",patientService.getAll(hospitalId));
        model.addAttribute("hospitalId", hospitalId);
        return "appointment/update";
    }

    @PostMapping("/{hospitalId}/{appointmentId}/update")
    public String update(@PathVariable Long hospitalId,
                         @PathVariable Long appointmentId,
                         @ModelAttribute("appointment") Appointment appointment) {
        appointmentService.update(appointmentId, appointment);
        return "redirect:/appointments/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{appointmentId}/delete")
    public String deleteById(@PathVariable Long hospitalId,
                             @PathVariable Long appointmentId) {
        appointmentService.deleteById(hospitalId,appointmentId);
        return "redirect:/appointments/"+hospitalId;
    }

}