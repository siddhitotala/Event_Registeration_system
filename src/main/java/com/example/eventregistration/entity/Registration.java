package com.example.eventregistration.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name="registrations", uniqueConstraints=@UniqueConstraint(name="uk_registration_event_email", columnNames={"event_id","email"}))
public class Registration {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(name="student_name", nullable=false, length=120)
    private String studentName;
    @Email @NotBlank @Column(nullable=false, length=190)
    private String email;
    @Column(name="college_id", length=80)
    private String collegeId;
    @Column(name="registered_at", nullable=false)
    private LocalDateTime registeredAt;
    @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="event_id", nullable=false)
    private Event event;

    public Registration() {}
    @PrePersist void onCreate(){if(registeredAt==null) registeredAt=LocalDateTime.now();}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getStudentName(){return studentName;} public void setStudentName(String studentName){this.studentName=studentName;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getCollegeId(){return collegeId;} public void setCollegeId(String collegeId){this.collegeId=collegeId;}
    public LocalDateTime getRegisteredAt(){return registeredAt;} public void setRegisteredAt(LocalDateTime registeredAt){this.registeredAt=registeredAt;}
    public Event getEvent(){return event;} public void setEvent(Event event){this.event=event;}
}
