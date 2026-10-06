package kigali.clinic.rw.service;

import kigali.clinic.rw.domain.*;
import kigali.clinic.rw.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repo;
    public AppointmentService(AppointmentRepository repo){this.repo=repo;}
    public List<Appointment> findAll(){return repo.findAll();}
    public Optional<Appointment> findById(Long id){return repo.findById(id);}
    public Appointment update(Appointment a){return repo.save(a);}
    public void delete(Long id){repo.deleteById(id);}
    public Object saveWithBookingCheck(Appointment a){
        if(repo.existsByDoctorIdAndAppointmentDateAndStatusNot(a.getDoctor().getId(), a.getAppointmentDate(), AppointmentStatus.CANCELLED))
            return "Doctor is already booked on that date";
        return repo.save(a);
    }
    public List<Appointment> byStatus(AppointmentStatus status){return repo.findByStatusOrderByAppointmentDateAsc(status);}
    public List<Appointment> between(LocalDate start, LocalDate end){return repo.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start,end);}
    public List<Object[]> statsByStatus(){return repo.countByStatus();}
    @Transactional public String cancelDoctorDay(Long doctorId, LocalDate date){return repo.cancelDoctorDay(doctorId,date)+" appointments cancelled";}
    public Page<Appointment> page(Pageable pageable){return repo.findAll(pageable);}
    @Transactional public String deleteCancelledBefore(LocalDate date){return repo.deleteCancelledBefore(date)+" appointments deleted";}
}
