package com.im_dr.Im_Dr.repo;

import com.im_dr.Im_Dr.Model.Admin;
import com.im_dr.Im_Dr.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

}
