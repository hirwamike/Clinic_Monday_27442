package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService service;
    public PatientController(PatientService service){this.service=service;}
    @PostMapping("/save") public Patient save(@RequestBody Patient p){return service.save(p);}
    @GetMapping public List<Patient> all(){return service.findAll();}
    @GetMapping("/{id}") public ResponseEntity<Patient> one(@PathVariable Long id){return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PutMapping("/update") public Patient update(@RequestBody Patient p){return service.update(p);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/by-last-name") public List<Patient> byLastName(@RequestParam String lastName){return service.byLastName(lastName);}
    @GetMapping("/of-doctor/{doctorId}") public ResponseEntity<?> ofDoctor(@PathVariable Long doctorId){return service.patientsOfDoctor(doctorId).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body("The doctor with that id does not exist"));}
    @GetMapping("/frequent") public List<Patient> frequent(@RequestParam long min){return service.frequent(min);}
}
