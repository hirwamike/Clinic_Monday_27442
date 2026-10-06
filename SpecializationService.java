package kigali.clinic.rw.service;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {
    private final SpecializationRepository repo;
    public SpecializationService(SpecializationRepository repo){this.repo=repo;}
    public Specialization save(Specialization s){return repo.save(s);}
    public List<Specialization> findAll(){return repo.findAll();}
    public Optional<Specialization> findById(Long id){return repo.findById(id);}
    public Specialization update(Specialization s){return repo.save(s);}
    public void delete(Long id){repo.deleteById(id);}
    public List<Specialization> unused(){return repo.findUnusedSpecializations();}
}
