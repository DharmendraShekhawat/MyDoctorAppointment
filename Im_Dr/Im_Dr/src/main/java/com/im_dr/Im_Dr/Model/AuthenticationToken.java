package com.im_dr.Im_Dr.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAuthentication")
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    private String tokenValue;
    private LocalDateTime tokenCreationTime;
    @Enumerated(value = EnumType.STRING)
    private AuthenticateType authenticateType;
    private  Integer userId;
//    private Integer authenticatorId;
    // type
    // userId
    // how to fetch class type on google

    //each token should be linked with a patient
          // patient.getClass()

    public AuthenticationToken(AuthenticateType userType, Integer id) {
        this.userId = id;
        this.authenticateType = userType;
        this.tokenCreationTime = LocalDateTime.now();
        this.tokenValue = UUID.randomUUID().toString();
    }
    //  patientId
//    @OneToOne
//    @JoinColumn(name = "fk_patient_id")
//    Patient patient;
//
//    @OneToOne
//    @JoinColumn(name = "fk_doctor_id")
//    Doctor doctor;

//    public AuthenticationToken(AuthenticateType userType, Doctor doctor) {
//        this.doctor = doctor;
//        this.authenticateType = userType;
//        this.tokenCreationTime = LocalDateTime.now();
//        this.tokenValue = UUID.randomUUID().toString();
//    }
}
