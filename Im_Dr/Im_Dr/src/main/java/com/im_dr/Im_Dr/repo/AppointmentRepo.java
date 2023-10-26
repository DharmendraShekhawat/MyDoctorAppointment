package com.im_dr.Im_Dr.repo;

import com.im_dr.Im_Dr.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {


}
