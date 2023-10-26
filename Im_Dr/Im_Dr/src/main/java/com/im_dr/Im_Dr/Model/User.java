package com.im_dr.Im_Dr.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {

    Doctor doctor;
    Patient patient;

//    String role;
//
//    public User(String role){
//       this.role = role;
//    }
}
