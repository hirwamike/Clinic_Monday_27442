package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.OfficeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {
    private final OfficeService service;
    public OfficeController(OfficeService service){this.service=service;}
    @PostMapping("/save") public Office save(@RequestBody Office o){return service.save(o);}
    @GetMapping public List<Office> all(){return service.findAll();}
    @GetMapping("/{id}") public ResponseEntity<Office> one(@PathVariable Long id){return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PutMapping("/update") public Office update(@RequestBody Office o){return service.update(o);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/busiest") public ResponseEntity<?> busiest(){return service.busiest().<ResponseEntity<?>>map(row->ResponseEntity.ok(row)).orElseGet(()->ResponseEntity.ok("No appointments yet"));}
    @GetMapping("/free") public List<Office> free(){return service.freeOffices();}
}
