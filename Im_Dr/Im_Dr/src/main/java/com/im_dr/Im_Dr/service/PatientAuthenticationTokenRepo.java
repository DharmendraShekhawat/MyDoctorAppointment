package com.im_dr.Im_Dr.service;

import com.im_dr.Im_Dr.Model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientAuthenticationTokenRepo extends JpaRepository<AuthenticationToken,Integer> {


    Optional<AuthenticationToken> findByTokenValue(String tokenValue);

@Query(value = "select token_id from pauthentication where user_id =:patientId AND authenticate_type =:type",nativeQuery = true)
  Integer findTokenIdByPatient(Integer patientId, String type);

    @Query(value = "select token_id from pauthentication where user_id =:docId AND authenticate_type =:type",nativeQuery = true)
    Integer findTokenIdByDoctorId(Integer docId, String type);
}
