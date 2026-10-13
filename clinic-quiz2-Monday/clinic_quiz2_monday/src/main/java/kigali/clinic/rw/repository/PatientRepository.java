package kigali.clinic.rw.repository;

import kigali.clinic.rw.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT p FROM Patient p JOIN p.appointments a WHERE a.doctor.id = :doctorId")
    List<Patient> findPatientsOfDoctor(Long doctorId);
    @org.springframework.data.jpa.repository.Query("SELECT p FROM Patient p JOIN p.appointments a GROUP BY p HAVING COUNT(a) >= :min ORDER BY COUNT(a) DESC")
    List<Patient> findFrequentPatients(long min);
    Optional<Patient> findBySsNumber(String ssNumber);
    boolean existsBySsNumber(String ssNumber);
}
