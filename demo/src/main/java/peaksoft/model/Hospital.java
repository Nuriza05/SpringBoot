package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
public class Hospital {
    @Id
    @SequenceGenerator(
            name = "hospital_gen",
            sequenceName = "hospital_seq",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hospital_gen")
    private Long id;
    @NotEmpty(message = "Name shouldn't be empty!")

    private String name;
    @NotEmpty(message = "Address shouldn't be empty!")
    private String address;
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Doctor> doctors;
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Patient> patients;
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Department> departments;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @NotEmpty(message = "Image shouldn't be empty!")
    private String imageLink;

    public void addAppoint(Appointment app) {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        appointments.add(app);
    }


}