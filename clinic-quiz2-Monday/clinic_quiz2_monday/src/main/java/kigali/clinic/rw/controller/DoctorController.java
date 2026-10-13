package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;
import kigali.clinic.rw.repository.SpecializationRepository;
import kigali.clinic.rw.repository.OfficeRepository;
import kigali.clinic.rw.repository.AppointmentRepository;
import java.time.LocalDate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService service; private final SpecializationRepository specializations; private final OfficeRepository offices; private final AppointmentRepository appointments;
    public DoctorController(DoctorService service, SpecializationRepository specializations, OfficeRepository offices, AppointmentRepository appointments){this.service=service;this.specializations=specializations;this.offices=offices;this.appointments=appointments;}
    @PostMapping("/save") public Doctor save(@RequestBody Doctor d){return service.save(d);}
    @GetMapping public List<Doctor> all(){return service.findAll();}
    @GetMapping("/{id}") public ResponseEntity<Doctor> one(@PathVariable Long id){return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PutMapping("/update") public Doctor update(@RequestBody Doctor d){return service.update(d);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/by-specialization") public List<Doctor> bySpecialization(@RequestParam String name){return service.bySpecialization(name);}
    @GetMapping("/without-office") public List<Doctor> withoutOffice(){return service.withoutOffice();}
    @PutMapping("/{doctorId}/specializations/{specializationId}") public ResponseEntity<String> addSpecialization(@PathVariable Long doctorId,@PathVariable Long specializationId){String m=service.addSpecialization(doctorId,specializationId,specializations);if(m.equals("The doctor with that id does not exist")||m.equals("That specialization does not exist"))return ResponseEntity.status(404).body(m);if(m.equals("The doctor already has that specialization"))return ResponseEntity.status(409).body(m);return ResponseEntity.ok(m);}
    @DeleteMapping("/{doctorId}/specializations/{specializationId}") public ResponseEntity<String> removeSpecialization(@PathVariable Long doctorId,@PathVariable Long specializationId){String m=service.removeSpecialization(doctorId,specializationId,specializations);if(m.equals("The doctor with that id does not exist")||m.equals("That specialization does not exist"))return ResponseEntity.status(404).body(m);if(m.equals("The doctor does not have that specialization"))return ResponseEntity.status(409).body(m);return ResponseEntity.ok(m);}
    @PatchMapping("/{doctorId}/office") public ResponseEntity<String> moveOffice(@PathVariable Long doctorId,@RequestParam Integer officeNumber){String m=service.moveOffice(doctorId,officeNumber,offices);if(m.equals("The doctor with that id does not exist")||m.equals("The office with that office number does not exist"))return ResponseEntity.status(404).body(m);if(m.equals("That office is taken by other doctor"))return ResponseEntity.status(409).body(m);return ResponseEntity.ok(m);}
    @GetMapping("/{doctorId}/agenda") public ResponseEntity<?> agenda(@PathVariable Long doctorId,@RequestParam String date){if(service.findById(doctorId).isEmpty())return ResponseEntity.status(404).body("The doctor with that id does not exist");return ResponseEntity.ok(serviceAgenda(doctorId,LocalDate.parse(date)));}
    private java.util.List<kigali.clinic.rw.domain.Appointment> serviceAgenda(Long id,LocalDate date){return appointments.findByDoctorIdAndAppointmentDateAndStatusNotOrderByAppointmentDateAsc(id,date,kigali.clinic.rw.domain.AppointmentStatus.CANCELLED);}
    @GetMapping("/search") public List<Doctor> search(@RequestParam String name){return service.searchByName(name);}
    @GetMapping("/workload") public List<Object[]> workload(@RequestParam String start,@RequestParam String end){return service.workload(LocalDate.parse(start),LocalDate.parse(end));}
}
