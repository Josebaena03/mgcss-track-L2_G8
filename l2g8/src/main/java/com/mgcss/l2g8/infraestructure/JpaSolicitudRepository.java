package com.mgcss.l2g8.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long> {
}