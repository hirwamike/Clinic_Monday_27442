package kigali.clinic.rw.service;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {
    private final OfficeRepository repo;
    public OfficeService(OfficeRepository repo){this.repo=repo;}
    public Office save(Office o){return repo.save(o);}
    public List<Office> findAll(){return repo.findAll();}
    public Optional<Office> findById(Long id){return repo.findById(id);}
    public Office update(Office o){return repo.save(o);}
    public void delete(Long id){repo.deleteById(id);}
    public Optional<Object[]> busiest(){return repo.findBusiestOffice().stream().findFirst();}
    public java.util.List<Office> freeOffices(){ return repo.findFreeOffices(); }
}
