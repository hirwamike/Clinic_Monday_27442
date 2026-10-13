package kigali.clinic.rw.controller;
import kigali.clinic.rw.domain.Prescription; import kigali.clinic.rw.service.*; import kigali.clinic.rw.repository.AppointmentRepository; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/prescriptions") public class PrescriptionController {
 private final PrescriptionService service; private final AppointmentService appointments; private final AppointmentRepository appointmentRepo;
 public PrescriptionController(PrescriptionService s,AppointmentService a,AppointmentRepository r){service=s;appointments=a;appointmentRepo=r;}
 @PostMapping("/save") public ResponseEntity<?> save(@RequestBody Prescription p,@RequestParam Long appointmentId){Object r=service.save(p,appointmentId);if(r.equals("The appointment with that id does not exist"))return ResponseEntity.status(404).body(r);if(r.equals("Prescriptions are only for COMPLETED appointments"))return ResponseEntity.badRequest().body(r);return ResponseEntity.status(201).body(r);}
 @GetMapping("/appointment/{appointmentId}") public ResponseEntity<?> byAppointment(@PathVariable Long appointmentId){if(appointmentRepo.findById(appointmentId).isEmpty())return ResponseEntity.status(404).body("The appointment with that id does not exist");return ResponseEntity.ok(service.byAppointment(appointmentId));}
 @GetMapping("/patient/{ssNumber}") public List<Prescription> history(@PathVariable String ssNumber){return service.history(ssNumber);}
 @PutMapping("/update/{id}") public ResponseEntity<String> update(@PathVariable java.util.UUID id,@RequestBody Prescription p){String m=service.update(id,p);if(m.startsWith("The prescription"))return ResponseEntity.status(404).body(m);return ResponseEntity.ok(m);}
 @DeleteMapping("/delete/{id}") public ResponseEntity<String> delete(@PathVariable java.util.UUID id){String m=service.delete(id);if(m.startsWith("The prescription"))return ResponseEntity.status(404).body(m);return ResponseEntity.ok(m);}
 @GetMapping("/stats/top-medicines") public List<Object[]> topMedicines(){return service.topMedicines();}
}
