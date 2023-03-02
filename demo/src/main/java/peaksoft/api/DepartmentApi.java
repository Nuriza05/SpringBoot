package peaksoft.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.exceptions.MyException;
import peaksoft.model.Department;
import peaksoft.service.DepartmentService;


@Controller
@RequestMapping("/departments")
public class DepartmentApi {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentApi(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{hospitalId}")
    public String findAll(@PathVariable Long hospitalId,Model model) {
        model.addAttribute("departments", departmentService.getAll(hospitalId));
        model.addAttribute(hospitalId);
        return "department/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newDepartment", new Department());
        model.addAttribute(hospitalId);
        return "department/savePage";
    }

    @PostMapping("/{hospitalId}/savePage")
    public String save(@PathVariable Long hospitalId,
                       @ModelAttribute("newDepartment")
                       Department department) throws MyException {
        departmentService.save(hospitalId,department);
        return "redirect:/departments/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{departmentId}/delete")
    public String deleteById(@PathVariable Long hospitalId,
                             @PathVariable Long departmentId) {
        departmentService.deleteById(departmentId);
        return "redirect:/departments/"+hospitalId;

    }

    @GetMapping("/{hospitalId}/{departmentId}/edit")
    public String edit(Model model, @PathVariable Long departmentId,
                       @PathVariable Long hospitalId) {
        model.addAttribute(departmentService.getById(departmentId));
        model.addAttribute("hospitalId", hospitalId);
        return "department/update";
    }

    @PostMapping("/{hospitalId}/{departmentId}/update")
    public String update(@PathVariable("hospitalId") Long hospitalId,
                         @PathVariable("departmentId")Long id,
                         @ModelAttribute("department") Department department) throws MyException {
        departmentService.update(id, department);
        return "redirect:/departments/"+hospitalId;
    }
}