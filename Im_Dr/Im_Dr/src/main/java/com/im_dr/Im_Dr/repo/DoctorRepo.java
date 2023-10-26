package com.im_dr.Im_Dr.repo;

import com.im_dr.Im_Dr.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findFirstByDocEmail(String userEmail);
}
