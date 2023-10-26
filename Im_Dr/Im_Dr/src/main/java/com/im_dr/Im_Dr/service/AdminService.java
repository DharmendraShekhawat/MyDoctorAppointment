package com.im_dr.Im_Dr.service;

import com.im_dr.Im_Dr.Model.*;
import com.im_dr.Im_Dr.repo.AdminRepo;
import com.im_dr.Im_Dr.repo.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;





    public ResponseEntity<String> singUp(Optional<User> user) {

        Optional<Patient> patient1 = Optional.ofNullable(user.get().getPatient());
        Optional<Doctor> doctor1 = Optional.ofNullable(user.get().getDoctor());

        if(doctor1.isPresent() && user.get().getDoctor().getClass().equals(Doctor.class)){
        return    doctorService.singUpDoctor( user.get().getDoctor());
        }
        else if(patient1.isPresent() && user.get().getPatient().getClass().equals(Patient.class)) {
          return   patientService.singUpPatients(user.get().getPatient());
        }
        else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body("not added, server problem try after some time !!! !!! ");
        }
    }


    public String userSignUp(Admin admin) {
        adminRepo.save(admin);
        return "admin  added";
    }


}
