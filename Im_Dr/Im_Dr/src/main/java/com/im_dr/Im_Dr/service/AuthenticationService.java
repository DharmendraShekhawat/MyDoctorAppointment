package com.im_dr.Im_Dr.service;

import com.im_dr.Im_Dr.Model.AuthenticateType;
import com.im_dr.Im_Dr.Model.Doctor;
import com.im_dr.Im_Dr.Model.Patient;
import com.im_dr.Im_Dr.Model.AuthenticationToken;
import com.im_dr.Im_Dr.Model.dto.PatientAuthenticateDTO;
import com.im_dr.Im_Dr.exceptionHandler.DoctorNotFoundException;
import com.im_dr.Im_Dr.repo.DoctorRepo;
import com.im_dr.Im_Dr.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthenticationService {


    @Autowired
    PatientAuthenticationTokenRepo patientAuthenticationTokenRepo;

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    DoctorRepo doctorRepo;

    public boolean Authenticate(PatientAuthenticateDTO patientAuthenticateDTO) {
        String eMail = patientAuthenticateDTO.getEmail();
        String tokenValue = patientAuthenticateDTO.getTokenValue();

        Optional<AuthenticationToken> tokenOptional = patientAuthenticationTokenRepo.findByTokenValue(tokenValue);

        Optional<Patient> patient = patientRepo.findFirstByPatientEmail(eMail);
        if (tokenOptional.get().getUserId().equals(patient.get().getPatientId())) {
            return true;
        } else {
            return false;
        }
    }


    public boolean authenticator(Doctor doctor, String userPassword)  {

//      Optional<Doctor> doctorOptional =  doctorRepo.findFirstByDocEmail(userEmail);

//      if(doctorOptional.isPresent()) {
          String encryptedPassword;
          try {
              encryptedPassword = PasswordEncrypte.encrypt(userPassword);
          } catch (Exception NoSuchAlgorithException) {
              return false;
          }

          return doctor.getDocPassword().equals(encryptedPassword);
          }

      }
