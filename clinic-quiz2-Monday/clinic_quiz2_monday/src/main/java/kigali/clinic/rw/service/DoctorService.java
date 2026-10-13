package kigali.clinic.rw.service;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository repo;
    public DoctorService(DoctorRepository repo){this.repo=repo;}
    public Doctor save(Doctor d){return repo.save(d);}
    public List<Doctor> findAll(){return repo.findAll();}
    public Optional<Doctor> findById(Long id){return repo.findById(id);}
    public Doctor update(Doctor d){return repo.save(d);}
    public void delete(Long id){repo.deleteById(id);}
    public List<Doctor> bySpecialization(String name){return repo.findBySpecializationNameIgnoreCase(name);}
    public List<Doctor> withoutOffice(){return repo.findDoctorsWithoutOffice();}
    public String addSpecialization(Long doctorId, Long specializationId, kigali.clinic.rw.repository.SpecializationRepository specs) {
        var doctor = repo.findById(doctorId);
        if (doctor.isEmpty()) return "The doctor with that id does not exist";
        var spec = specs.findById(specializationId);
        if (spec.isEmpty()) return "That specialization does not exist";
        if (doctor.get().getSpecializations().contains(spec.get())) return "The doctor already has that specialization";
        doctor.get().getSpecializations().add(spec.get()); repo.save(doctor.get()); return "Specialization added to the doctor";
    }
    public String removeSpecialization(Long doctorId, Long specializationId, kigali.clinic.rw.repository.SpecializationRepository specs) {
        var doctor=repo.findById(doctorId); if(doctor.isEmpty()) return "The doctor with that id does not exist";
        var spec=specs.findById(specializationId); if(spec.isEmpty()) return "That specialization does not exist";
        if(!doctor.get().getSpecializations().contains(spec.get())) return "The doctor does not have that specialization";
        doctor.get().getSpecializations().remove(spec.get()); repo.save(doctor.get()); return "Specialization removed from the doctor";
    }
    public String moveOffice(Long doctorId, Integer officeNumber, kigali.clinic.rw.repository.OfficeRepository offices) {
        var d=repo.findById(doctorId); if(d.isEmpty()) return "The doctor with that id does not exist";
        var o=offices.findByOfficeNumber(officeNumber); if(o.isEmpty()) return "The office with that office number does not exist";
        if(repo.existsByOfficeId(o.get().getId()) && (d.get().getOffice()==null || !d.get().getOffice().getId().equals(o.get().getId()))) return "That office is taken by other doctor";
        d.get().setOffice(o.get()); repo.save(d.get()); return "Office changed successfully";
    }
    public List<Doctor> searchByName(String name){return repo.searchByName(name);}
    public List<Object[]> workload(java.time.LocalDate start, java.time.LocalDate end){return repo.workload(start,end);}
}
