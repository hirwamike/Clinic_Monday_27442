package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.service.AppointmentService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService service;
    public AppointmentController(AppointmentService service){this.service=service;}
    @PostMapping("/save") public ResponseEntity<?> save(@RequestBody Appointment a){
        Object result=service.saveWithBookingCheck(a);
        if(result instanceof String) return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        return ResponseEntity.ok(result);
    }
    @GetMapping public List<Appointment> all(){return service.findAll();}
    @GetMapping("/{id}") public ResponseEntity<Appointment> one(@PathVariable Long id){return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PutMapping("/update") public Appointment update(@RequestBody Appointment a){return service.update(a);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
    @GetMapping("/by-status") public List<Appointment> byStatus(@RequestParam AppointmentStatus status){return service.byStatus(status);}
    @GetMapping("/between") public List<Appointment> between(@RequestParam String start,@RequestParam String end){return service.between(LocalDate.parse(start),LocalDate.parse(end));}
    @GetMapping("/stats/by-status") public List<Object[]> stats(){return service.statsByStatus();}
    @PatchMapping("/cancel-day") public String cancelDay(@RequestParam Long doctorId,@RequestParam String date){return service.cancelDoctorDay(doctorId,LocalDate.parse(date));}
    @GetMapping("/page") public Page<Appointment> page(@RequestParam int page,@RequestParam int size,@RequestParam String sort){
        String[] parts=sort.split(",",2); String property=parts[0]; Sort.Direction direction=parts.length>1 && parts[1].equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
        return service.page(PageRequest.of(page,size,Sort.by(direction,property)));
    }
    @DeleteMapping("/cancelled-before") public String deleteCancelledBefore(@RequestParam String date){return service.deleteCancelledBefore(LocalDate.parse(date));}
}
