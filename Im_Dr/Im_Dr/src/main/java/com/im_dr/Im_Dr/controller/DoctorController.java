package com.im_dr.Im_Dr.controller;

import com.im_dr.Im_Dr.exceptionHandler.DoctorNotFoundException;
import com.im_dr.Im_Dr.service.DoctorService;
import com.im_dr.Im_Dr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @PostMapping("/doctor/signIn")
    public ResponseEntity<String> doctorSignIn(@RequestParam String docEmail,@RequestParam String docPassword) throws DoctorNotFoundException {
        return userService.userSignIn(docEmail, docPassword);
    }


}
