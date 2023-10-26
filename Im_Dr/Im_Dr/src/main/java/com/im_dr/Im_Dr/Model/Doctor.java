package com.im_dr.Im_Dr.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,scope = Doctor.class,property = "docId")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docId;
    private String  docName;
    private String docEmail;
    private String docPassword;
    private double  docFee;

    @Enumerated(value = EnumType.STRING)
    private Specialization docSpecialization;

    @Enumerated(value = EnumType.STRING)
    private Qualification docQualification;

    private String docContact;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    List<Appointment> appointments;
}
