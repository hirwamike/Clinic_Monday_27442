package kigali.clinic.rw.controller;
import kigali.clinic.rw.service.DashboardService; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/dashboard") public class DashboardController {private final DashboardService service; public DashboardController(DashboardService s){service=s;} @GetMapping("/summary") public Map<String,Long> summary(){return service.summary();}}
