package kigali.clinic.rw.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @Column(length = 36)
    private UUID id = UUID.randomUUID();
    @Column(nullable = false) private String medicineName;
    @Column(nullable = false) private String dosage;
    @Column(nullable = false) private int durationDays;
    @Column(nullable = false) private LocalDate issuedDate;
    @ManyToOne(optional = false) @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    public UUID getId(){return id;} public void setId(UUID id){this.id=id;}
    public String getMedicineName(){return medicineName;} public void setMedicineName(String v){medicineName=v;}
    public String getDosage(){return dosage;} public void setDosage(String v){dosage=v;}
    public int getDurationDays(){return durationDays;} public void setDurationDays(int v){durationDays=v;}
    public LocalDate getIssuedDate(){return issuedDate;} public void setIssuedDate(LocalDate v){issuedDate=v;}
    public Appointment getAppointment(){return appointment;} public void setAppointment(Appointment v){appointment=v;}
}
