package kigali.clinic.rw.service;
import kigali.clinic.rw.domain.*; import kigali.clinic.rw.repository.*; import org.springframework.stereotype.Service; import java.time.LocalDate; import java.util.*;
@Service public class DashboardService {
 private final PatientRepository patients; private final DoctorRepository doctors; private final OfficeRepository offices; private final AppointmentRepository appointments; private final PrescriptionRepository prescriptions;
 public DashboardService(PatientRepository p,DoctorRepository d,OfficeRepository o,AppointmentRepository a,PrescriptionRepository pr){patients=p;doctors=d;offices=o;appointments=a;prescriptions=pr;}
 public Map<String,Long> summary(){Map<String,Long> m=new LinkedHashMap<>();m.put("patients",patients.count());m.put("doctors",doctors.count());m.put("freeOffices",(long)offices.findFreeOffices().size());m.put("appointmentsToday",appointments.countByAppointmentDate(LocalDate.now()));m.put("scheduled",appointments.countByStatus(AppointmentStatus.SCHEDULED));m.put("prescriptions",prescriptions.count());return m;}
}
