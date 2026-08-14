package com.testvito.backendtestvito.repository;

import com.testvito.backendtestvito.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}