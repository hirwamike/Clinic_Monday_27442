package kigali.clinic.rw.service;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository repo; private final DoctorRepository doctors;
    public PatientService(PatientRepository repo, DoctorRepository doctors){this.repo=repo;this.doctors=doctors;}
    public Patient save(Patient p){return repo.save(p);}
    public List<Patient> findAll(){return repo.findAll();}
    public Optional<Patient> findById(Long id){return repo.findById(id);}
    public Patient update(Patient p){return repo.save(p);}
    public void delete(Long id){repo.deleteById(id);}
    public List<Patient> byLastName(String lastName){return repo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);}
    public Optional<List<Patient>> patientsOfDoctor(Long doctorId){
        if(!doctors.existsById(doctorId)) return Optional.empty();
        return Optional.of(repo.findPatientsOfDoctor(doctorId));
    }
    public List<Patient> frequent(long min){return repo.findFrequentPatients(min);}
}
