package com.im_dr.Im_Dr.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class PatientSignInDTO {

    String email;
    String password;

}
