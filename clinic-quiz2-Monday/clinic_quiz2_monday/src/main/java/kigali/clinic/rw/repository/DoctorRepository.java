package kigali.clinic.rw.repository;

import kigali.clinic.rw.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.time.LocalDate;
import kigali.clinic.rw.domain.AppointmentStatus;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT DISTINCT d FROM Doctor d JOIN d.specializations s WHERE LOWER(s.name) = LOWER(:name)")
    List<Doctor> findBySpecializationNameIgnoreCase(String name);
    @Query("SELECT d FROM Doctor d WHERE d.office IS NULL ORDER BY d.lastName ASC")
    List<Doctor> findDoctorsWithoutOffice();
    boolean existsByOfficeId(Long officeId);
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY d.lastName ASC")
    List<Doctor> searchByName(@Param("name") String name);
    @Query("SELECT d.firstName, d.lastName, COUNT(a) FROM Doctor d JOIN d.appointments a WHERE a.status = kigali.clinic.rw.domain.AppointmentStatus.COMPLETED AND a.appointmentDate BETWEEN :start AND :end GROUP BY d.id, d.firstName, d.lastName ORDER BY COUNT(a) DESC")
    List<Object[]> workload(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
