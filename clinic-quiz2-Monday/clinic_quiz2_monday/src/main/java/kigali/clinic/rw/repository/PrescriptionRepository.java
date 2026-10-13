package kigali.clinic.rw.repository;
import kigali.clinic.rw.domain.Prescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List; import java.util.UUID;
public interface PrescriptionRepository extends JpaRepository<Prescription,UUID> {
 List<Prescription> findByAppointmentId(Long appointmentId);
 boolean existsByAppointmentId(Long appointmentId);
 @Query("SELECT p FROM Prescription p WHERE p.appointment.patient.ssNumber = :ss ORDER BY p.issuedDate DESC")
 List<Prescription> findPatientHistory(@Param("ss") String ssNumber);
 @Query("SELECT p.medicineName, COUNT(p) FROM Prescription p GROUP BY p.medicineName ORDER BY COUNT(p) DESC")
 List<Object[]> findTopMedicines();
}
