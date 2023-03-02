package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
public class Doctor {
    @Id
    @SequenceGenerator(
            name = "doctor_gen",
            sequenceName = "doctors_seq",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "doctor_gen")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    private String firstName;

    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    @Column(name = "last_name")
    private String lastName;

    private String position;


    @NotEmpty(message = "Email shouldn't be empty!")
    @Email(message = "should be valid!")
    @Column(unique = true)
    private String email;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE
    })

    private List<Department> departments = new ArrayList<>();

    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
    private Hospital hospital;
    @NotEmpty(message = "Image shouldn't be empty!")
    private String imageLink;
    @Transient
    private Long hospitalId;
    @Transient
    private List<Long> departmentId = new ArrayList<>();
    public void setDepartment(Department byId) {
        departments.add(byId);
    }
}