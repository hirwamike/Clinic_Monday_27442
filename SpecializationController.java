package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.SpecializationService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {
    private final SpecializationService service;
    public SpecializationController(SpecializationService service){this.service=service;}
    @PostMapping("/save") public Specialization save(@RequestBody Specialization s){return service.save(s);}
    @GetMapping public List<Specialization> all(){return service.findAll();}
    @GetMapping("/{id}") public ResponseEntity<Specialization> one(@PathVariable Long id){return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PutMapping("/update") public Specialization update(@RequestBody Specialization s){return service.update(s);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/unused") public List<Specialization> unused(){return service.unused();}
}
