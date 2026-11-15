package com.agriot.agroiot.repository;

import com.agriot.agroiot.model.LecturaSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturaRepository extends JpaRepository<LecturaSensor, Long> {


    List<LecturaSensor> findTop20ByOrderByFechaRegistroDesc();

    Optional<LecturaSensor> findFirstByModuloOrderByFechaRegistroDesc(String modulo);
}