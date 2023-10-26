package com.im_dr.Im_Dr.service;

import com.im_dr.Im_Dr.Model.Appointment;
import com.im_dr.Im_Dr.Model.AuthenticateType;
import com.im_dr.Im_Dr.Model.Patient;
import com.im_dr.Im_Dr.Model.AuthenticationToken;
import com.im_dr.Im_Dr.Model.dto.PatientAppointmentScheduleDTO;
import com.im_dr.Im_Dr.Model.dto.PatientSignInDTO;
import com.im_dr.Im_Dr.Model.dto.PatientAuthenticateDTO;
import com.im_dr.Im_Dr.repo.AppointmentRepo;
import com.im_dr.Im_Dr.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    PatientAuthenticationTokenRepo patientAuthenticationTokenRepo;
    @Autowired
    AuthenticationService patientAuthenticationTokenService;
    @Autowired
    AppointmentRepo appointmentRepo;


    public ResponseEntity<String> singUpPatients(Patient patient) {
        Optional<Patient> optionalPatient = patientRepo.findFirstByPatientEmail(patient.getPatientEmail());
//        String patientEmail = patient.getPatientEmail();

//        if (patientRepo.findFirstByPatientEmail(patientEmail) == null) {

        if(optionalPatient.isEmpty()){
            String patientPassword = patient.getPatientPassword();
            try {
                String encryptedPassword = PasswordEncrypte.encrypt(patientPassword);
                patient.setPatientPassword(encryptedPassword);
            } catch (Exception e) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(status).body("server issue try after some time");
            }
            patientRepo.save(patient);
            HttpStatus status = HttpStatus.CREATED;
            return ResponseEntity.status(status).body("patient added");
        }
        else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body("email already register");
        }
    }

    public ResponseEntity<String> singInPatients(PatientSignInDTO patientSignInDTO) {

        Optional<Patient> optionalPatient = patientRepo.findFirstByPatientEmail(patientSignInDTO.getEmail());
        if (optionalPatient.isPresent()) {

            if (patientAuthenticationTokenRepo.findTokenIdByPatient(optionalPatient.get().getPatientId(),AuthenticateType.PATIENT.name())  == null) {


                String encryptedPassword;
                try {
                    encryptedPassword = PasswordEncrypte.encrypt(patientSignInDTO.getPassword());
                } catch (Exception e) {
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    return ResponseEntity.status(status).body("server problem try after sometime");
                }
                Patient patient = optionalPatient.get();

                if (patient.getPatientPassword().equals(encryptedPassword)) {
                    AuthenticationToken token = new AuthenticationToken(AuthenticateType.PATIENT,patient.getPatientId());
                    patientAuthenticationTokenRepo.save(token);
                    HttpStatus status = HttpStatus.ACCEPTED;
                    return ResponseEntity.status(status).body("tokenId " + token.getTokenValue());
                } else {
                    HttpStatus status = HttpStatus.FORBIDDEN;
                    return ResponseEntity.status(status).body("Credentials Issue");
                }
            } else {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(status).body(" You have loggedIn other Device please signOut");
            }
        }
        else {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(status).body("please signUp first");
            }
        }

    public ResponseEntity<String> singOutPatients(PatientAuthenticateDTO patientSignOutDTO) {

        Optional<AuthenticationToken> token = patientAuthenticationTokenRepo.findByTokenValue(patientSignOutDTO.getTokenValue());
        if(token.isPresent()){
        patientAuthenticationTokenRepo.delete(token.get());
        HttpStatus status = HttpStatus.OK;
        return ResponseEntity.status(status).body("signOut successful");
    }
      else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body("Un Authenticated access!!!");
        }
        }

    public ResponseEntity<String> patientAppointmentBooking(PatientAppointmentScheduleDTO patientAppointmentScheduleDTO) {

        PatientAuthenticateDTO patientAuthenticateDTO = patientAppointmentScheduleDTO.getPatientAuthenticateDTO();
              // -------find patient for appointment-------------
//        Optional<Patient> patientOptional = patientRepo.findFirstByPatientEmail(patientAuthenticateDTO.getEmail());


        if(patientAuthenticationTokenService.Authenticate(patientAuthenticateDTO)){
            Appointment appointment = patientAppointmentScheduleDTO.getAppointment();
//            appointment.setPatient(patientAppointmentScheduleDTO.getAppointment().getPatient());
//            appointment.setDoctor(appointment.getDoctor());
            appointmentRepo.save(appointment);
            HttpStatus status = HttpStatus.CREATED;
         return    ResponseEntity.status(status).body("appointment booked with  "+ appointment.getAppointmentId());
        }
     else {
            HttpStatus status = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
            return ResponseEntity.status(status).body("Credential issue!!! ");
        }
    }

    public ResponseEntity<String> patientPasswordReset(String email, LocalDate patientDateOFBirth, String newPassword) {

        try {
            Optional<Patient> patientOptional = patientRepo.findFirstByPatientEmail(email);
            if(patientOptional.get().getPatientDateOFBirth().equals(patientDateOFBirth)){
                patientOptional.get().setPatientPassword(PasswordEncrypte.encrypt(newPassword));
                patientRepo.save(patientOptional.get());

                Integer tokenId =  patientAuthenticationTokenRepo.findTokenIdByPatient(patientOptional.get().getPatientId(),AuthenticateType.PATIENT.name());

                if(tokenId != null){

                Optional<AuthenticationToken> token =  patientAuthenticationTokenRepo.findById(tokenId);
                    patientAuthenticationTokenRepo.delete(token.get());
                }

                HttpStatus status = HttpStatus.ACCEPTED;
                return ResponseEntity.status(status).body("your password is updated");
            }
            else {
                HttpStatus status = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
                return ResponseEntity.status(status).body("Credential issue!!! ");
            }

        }catch (Exception NullPointerException){
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body("Fields mismatching !!! ");
        }

    }
}
