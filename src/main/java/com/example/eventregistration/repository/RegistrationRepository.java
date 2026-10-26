package com.example.eventregistration.repository;
import com.example.eventregistration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByEventIdAndEmailIgnoreCase(Long eventId, String email);
    long countByEventId(Long eventId);
    List<Registration> findByEventIdOrderByRegisteredAtDesc(Long eventId);
}
