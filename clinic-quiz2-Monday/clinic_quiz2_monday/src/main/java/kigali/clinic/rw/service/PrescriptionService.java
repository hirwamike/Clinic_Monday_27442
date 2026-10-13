package kigali.clinic.rw.service;
import kigali.clinic.rw.domain.*; import kigali.clinic.rw.repository.*; import org.springframework.stereotype.Service; import java.time.LocalDate; import java.util.*;
@Service public class PrescriptionService {
 private final PrescriptionRepository repo; private final AppointmentRepository appointments; private final PatientRepository patients;
 public PrescriptionService(PrescriptionRepository r,AppointmentRepository a,PatientRepository p){repo=r;appointments=a;patients=p;}
 public Object save(Prescription p,Long appointmentId){var a=appointments.findById(appointmentId);if(a.isEmpty())return "The appointment with that id does not exist";if(a.get().getStatus()!=AppointmentStatus.COMPLETED)return "Prescriptions are only for COMPLETED appointments";p.setAppointment(a.get());p.setIssuedDate(LocalDate.now());repo.save(p);return "Prescription saved successfully";}
 public List<Prescription> byAppointment(Long id){return repo.findByAppointmentId(id);} public List<Prescription> history(String ss){return repo.findPatientHistory(ss);} public Optional<Prescription> findById(UUID id){return repo.findById(id);}
 public String update(UUID id,Prescription incoming){var old=repo.findById(id);if(old.isEmpty())return "The prescription with that id does not exist";var p=old.get();p.setMedicineName(incoming.getMedicineName());p.setDosage(incoming.getDosage());p.setDurationDays(incoming.getDurationDays());repo.save(p);return "Prescription updated successfully";}
 public String delete(UUID id){var old=repo.findById(id);if(old.isEmpty())return "The prescription with that id does not exist";repo.delete(old.get());return "Prescription deleted successfully";}
 public boolean hasForAppointment(Long id){return repo.existsByAppointmentId(id);} public long count(){return repo.count();} public List<Object[]> topMedicines(){return repo.findTopMedicines();}
}
