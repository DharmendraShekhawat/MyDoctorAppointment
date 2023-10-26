package com.im_dr.Im_Dr.controller;

import com.im_dr.Im_Dr.Model.Admin;
import com.im_dr.Im_Dr.Model.User;
import com.im_dr.Im_Dr.Model.dto.PatientAppointmentScheduleDTO;
import com.im_dr.Im_Dr.exceptionHandler.DoctorNotFoundException;
import com.im_dr.Im_Dr.service.AdminService;
import com.im_dr.Im_Dr.service.PatientService;
import com.im_dr.Im_Dr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    //    ------------------- adminSignUp -----------
    @PostMapping("/admin/SignUp")
    public ResponseEntity<String> userSignUp(@RequestBody Admin admin){
        String response =   userService.userSignUp(admin);
        HttpStatus status = HttpStatus.CREATED;
        return ResponseEntity.status(status).body(response);
    }

//    --------------------- userSignUp  as a Doctor Or as a Patient-----------
    @PostMapping("/user/signUp/doctorsOrPatient")
    public ResponseEntity<String> addUser(@RequestBody User user){
     return    adminService.singUp(Optional.ofNullable(user));

    }

    //    ------------------ userSignIn  as a Doctor Or as a Patient-----------
    @PostMapping("/user/signIn/doctorOrPatient")
    public ResponseEntity<String> doctorSignIn(@RequestParam String email, @RequestParam String password) throws DoctorNotFoundException {
        return userService.userSignIn(email, password);
    }

    // ------------------- Patient appointment schedule----------------
    @PostMapping("/patient/appointment/schedule")
    public ResponseEntity<String> patientAppointmentBooking(@RequestBody PatientAppointmentScheduleDTO patientAppointmentScheduleDTO ){
        return patientService.patientAppointmentBooking(patientAppointmentScheduleDTO);
    }

    }
