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
}
