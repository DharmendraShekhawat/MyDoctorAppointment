package com.im_dr.Im_Dr.repo;

import com.im_dr.Im_Dr.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient, Integer> {


   Optional<Patient> findFirstByPatientEmail(String patientEmail);


}
