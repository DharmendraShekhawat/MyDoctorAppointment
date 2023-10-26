package com.im_dr.Im_Dr.Model.dto;

import com.im_dr.Im_Dr.Model.Appointment;
import lombok.Data;

@Data
public class PatientAppointmentScheduleDTO {

    PatientAuthenticateDTO patientAuthenticateDTO;
    Appointment appointment;
}
