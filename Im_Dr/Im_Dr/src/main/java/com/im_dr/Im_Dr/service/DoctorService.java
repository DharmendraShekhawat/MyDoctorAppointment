package com.im_dr.Im_Dr.service;

import com.im_dr.Im_Dr.Model.AuthenticateType;
import com.im_dr.Im_Dr.Model.AuthenticationToken;
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

import javax.print.Doc;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    PatientService patientService;

    @Autowired
    PatientAuthenticationTokenRepo patientAuthenticationTokenRepo;


    public ResponseEntity<String> singUpDoctor(Doctor doctor)  {

        Optional<Doctor> doctorOptional = doctorRepo.findFirstByDocEmail(doctor.getDocEmail());

        if(doctorOptional.isEmpty()) {

            try {
                String encryptPassword = PasswordEncrypte.encrypt(doctor.getDocPassword());
                doctor.setDocPassword(encryptPassword);
                doctorRepo.save(doctor);
                HttpStatus status = HttpStatus.CREATED;
                return ResponseEntity.status(status).body("doctor added");
            } catch (Exception NoSuchAlgorithm) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(status).body("server problem try after some time !!! ");
            }
        }
        else{
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body("already registered please use another email or singIn !!! ");
        }
    }

    public ResponseEntity<String> doctorSignIn(String email, String password) throws DoctorNotFoundException {
//    try {
//        if (authenticationService.authenticator(docEmail, docPassword)) {
//            Optional<Doctor> doctorOptional = doctorRepo.findFirstByDocEmail(docEmail);
//            AuthenticationToken token = new AuthenticationToken(AuthenticateType.DOCTOR, doctorOptional.get());
//            token.setDoctor(doctorOptional.get());
//            patientAuthenticationTokenRepo.save(token);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body("tokenId" + token.getTokenValue());
//        }else{
//
//        }
//    }
////          return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Credential issue");
//  catch (Exception ex) {
//      throw new DoctorNotFoundException("Credential issue");
//
//  }
//        throw new DoctorNotFoundException("Credential issue");
//


//            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Credential issue");


        Optional<Doctor> doctorOptional = doctorRepo.findFirstByDocEmail(email);

        if (doctorOptional.isPresent()) {


            if (authenticationService.authenticator(doctorOptional.get(), password)) {

                if (patientAuthenticationTokenRepo.findTokenIdByDoctorId(doctorOptional.get().getDocId(),AuthenticateType.DOCTOR.name()) == null) {

                    AuthenticationToken token = new AuthenticationToken(AuthenticateType.DOCTOR, doctorOptional.get().getDocId());
                    patientAuthenticationTokenRepo.save(token);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("signIn as doctor with tokenId" + token.getTokenValue());
                } else {
                    return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("U have signIn in other device first signOut");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Credential issue");
            }
        }else {
            throw new DoctorNotFoundException("please signUp first");
        }
    }
}
