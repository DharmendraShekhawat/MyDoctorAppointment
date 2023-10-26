package com.im_dr.Im_Dr.controller;

import com.im_dr.Im_Dr.Model.Patient;
import com.im_dr.Im_Dr.Model.dto.PatientAppointmentScheduleDTO;
import com.im_dr.Im_Dr.Model.dto.PatientSignInDTO;
import com.im_dr.Im_Dr.Model.dto.PatientAuthenticateDTO;
import com.im_dr.Im_Dr.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

        // ---------- Patient SignUp----------------
    @PostMapping("/patient/singUp")
    public ResponseEntity<String> singUpPatients(@RequestBody Patient patient){
        return patientService.singUpPatients(patient);
    }
    // ---------- Patient SignIp----------------
    @PostMapping("/patient/singIn")
    public ResponseEntity<String> singInPatients(@RequestBody PatientSignInDTO patientSignInDTO ){
        return patientService.singInPatients(patientSignInDTO);
    }

    // ---------- Patient SignOut----------------
    @DeleteMapping("/patient/singOut")
    public ResponseEntity<String> singOutPatients(@RequestBody PatientAuthenticateDTO patientSignOutDTO ){
        return patientService.singOutPatients(patientSignOutDTO);
    }

    // ---------- Patient appointment schedule----------------
//    @PostMapping("/patient/appointment/schedule")
//    public ResponseEntity<String> patientAppointmentBooking(@RequestBody PatientAppointmentScheduleDTO patientAppointmentScheduleDTO ){
//        return patientService.patientAppointmentBooking(patientAppointmentScheduleDTO);
//    }
    // ---------- Patient passwordReset----------------
    @PutMapping("/patient/passwordReset")
    public ResponseEntity<String> passwordReset(@RequestParam String password, @RequestParam String email, @RequestParam LocalDate patientDateOFBirth){
        return patientService.patientPasswordReset(email, patientDateOFBirth, password);
    }

}
