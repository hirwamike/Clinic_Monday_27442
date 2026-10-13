package kigali.clinic.rw.service;
import kigali.clinic.rw.domain.*;
import kigali.clinic.rw.repository.AppointmentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate; import java.util.*;
@Service public class AppointmentService {
 private final AppointmentRepository repo; public AppointmentService(AppointmentRepository repo){this.repo=repo;}
 public List<Appointment> findAll(){return repo.findAll();} public Optional<Appointment> findById(Long id){return repo.findById(id);}
 public Appointment update(Appointment a){return repo.save(a);} public void delete(Long id){repo.deleteById(id);}
 public Object saveWithBookingCheck(Appointment a){ if(repo.existsByDoctorIdAndAppointmentDateAndStatusNot(a.getDoctor().getId(),a.getAppointmentDate(),AppointmentStatus.CANCELLED)) return "Doctor is already booked on that date"; return repo.save(a); }
 public List<Appointment> byStatus(AppointmentStatus status){return repo.findByStatusOrderByAppointmentDateAsc(status);}
 public List<Appointment> between(LocalDate start,LocalDate end){return repo.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start,end);}
 public List<Object[]> statsByStatus(){return repo.countByStatus();}
 @Transactional public String cancelDoctorDay(Long doctorId,LocalDate date){return repo.cancelDoctorDay(doctorId,date)+" appointments cancelled";}
 public Page<Appointment> page(Pageable pageable){return repo.findAll(pageable);}
 @Transactional public String deleteCancelledBefore(LocalDate date){return repo.deleteCancelledBefore(date)+" appointments deleted";}
 public List<Appointment> agenda(Long doctorId,LocalDate date){return repo.findByDoctorIdAndAppointmentDateAndStatusNotOrderByAppointmentDateAsc(doctorId,date,AppointmentStatus.CANCELLED);}
 public List<Appointment> upcoming(String ss){return repo.findByPatientSsNumberAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(ss,LocalDate.now(),List.of(AppointmentStatus.SCHEDULED,AppointmentStatus.CONFIRMED));}
 public String changeStatus(Long id,AppointmentStatus next){var opt=repo.findById(id);if(opt.isEmpty())return "The appointment with that id does not exist";var a=opt.get();var old=a.getStatus();boolean allowed=(old==AppointmentStatus.SCHEDULED&&(next==AppointmentStatus.CONFIRMED||next==AppointmentStatus.CANCELLED))||(old==AppointmentStatus.CONFIRMED&&(next==AppointmentStatus.COMPLETED||next==AppointmentStatus.CANCELLED));if(!allowed)return "Cannot change status from "+old+" to "+next;a.setStatus(next);repo.save(a);return "Appointment status updated successfully";}
 public String reschedule(Long id,LocalDate date){var opt=repo.findById(id);if(opt.isEmpty())return "The appointment with that id does not exist";var a=opt.get();if(a.getStatus()==AppointmentStatus.COMPLETED)return "A COMPLETED appointment cannot be rescheduled";if(a.getStatus()==AppointmentStatus.CANCELLED)return "A CANCELLED appointment cannot be rescheduled";if(date.isBefore(LocalDate.now()))return "Appointment date cannot be in the past";if(repo.existsByDoctorIdAndAppointmentDateAndStatusNot(a.getDoctor().getId(),date,AppointmentStatus.CANCELLED))return "Doctor is already booked on that date";a.setAppointmentDate(date);a.setStatus(AppointmentStatus.SCHEDULED);repo.save(a);return "Appointment rescheduled successfully";}
 public long todayCount(){return repo.countByAppointmentDate(LocalDate.now());} public long scheduledCount(){return repo.countByStatus(AppointmentStatus.SCHEDULED);}
}
