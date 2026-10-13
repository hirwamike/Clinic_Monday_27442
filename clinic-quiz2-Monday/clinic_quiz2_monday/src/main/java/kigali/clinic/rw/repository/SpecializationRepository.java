package kigali.clinic.rw.repository;

import kigali.clinic.rw.domain.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    @Query("SELECT s FROM Specialization s WHERE s.doctors IS EMPTY")
    List<Specialization> findUnusedSpecializations();
}
