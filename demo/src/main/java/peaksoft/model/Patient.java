package peaksoft.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.Gender;

import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {
    @Id
    @SequenceGenerator(name = "patient_gen", sequenceName = "patient_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_gen")
    private Long id;

    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty(message = "Email shouldn't be empty!")
    @Email(message = "Email should be valid!")
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private Hospital hospital;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appoitmentList;
    @Transient
    private Long hospitalId;


}
