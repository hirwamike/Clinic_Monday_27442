package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService service;
    public DoctorController(DoctorService service){this.service=service;}
    @PostMapping("/save") public Doctor save(@RequestBody Doctor d){return service.save(d);}
    @GetMapping public List<Doctor> all(){return service.findAll();}
    @GetMapping("/{id}") public ResponseEntity<Doctor> one(@PathVariable Long id){return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PutMapping("/update") public Doctor update(@RequestBody Doctor d){return service.update(d);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/by-specialization") public List<Doctor> bySpecialization(@RequestParam String name){return service.bySpecialization(name);}
    @GetMapping("/without-office") public List<Doctor> withoutOffice(){return service.withoutOffice();}
}
