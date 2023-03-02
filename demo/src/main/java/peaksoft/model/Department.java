package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @SequenceGenerator(name = "department_gen", sequenceName = "department_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "Name shouldn't be empty!")
    private String name;
    @ManyToMany(mappedBy = "departments",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST})

    private List<Doctor> doctors;
    public void addDoctor(Doctor doctor) {
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE
    })

    
    private Hospital hospital;
    @Transient
    private Long hospitalId;
}