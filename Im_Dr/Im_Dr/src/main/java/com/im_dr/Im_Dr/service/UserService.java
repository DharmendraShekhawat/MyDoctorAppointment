package com.im_dr.Im_Dr.service;

import com.im_dr.Im_Dr.Model.Admin;
import com.im_dr.Im_Dr.Model.Doctor;
import com.im_dr.Im_Dr.Model.Patient;
import com.im_dr.Im_Dr.Model.dto.PatientSignInDTO;
import com.im_dr.Im_Dr.exceptionHandler.DoctorNotFoundException;
import com.im_dr.Im_Dr.repo.DoctorRepo;
import com.im_dr.Im_Dr.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    AdminService adminService;

    public String userSignUp(Admin admin) {
      return   adminService.userSignUp(admin);
    }

    public ResponseEntity<String> userSignIn(String email, String password) throws DoctorNotFoundException {

        Optional<Doctor> doctorOptional1 = doctorRepo.findFirstByDocEmail(email);

        Optional<Patient> patientOptional =  patientRepo.findFirstByPatientEmail(email);

        if(patientOptional.isPresent()){

            PatientSignInDTO patientSignInDTO = new PatientSignInDTO(email, password);
          return   patientService.singInPatients(patientSignInDTO);

        }
        else if(doctorOptional1.isPresent()){
          return   doctorService.doctorSignIn(email,password);
        }
        else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(" please signUp first");
        }

    }


}
