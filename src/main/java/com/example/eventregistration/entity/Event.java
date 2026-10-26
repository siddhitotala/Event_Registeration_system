package com.example.eventregistration.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, length = 120)
    private String name;
    @Column(length = 2000)
    private String description;
    @NotNull @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;
    @Column(name = "event_time")
    private LocalTime eventTime;
    @NotBlank @Column(nullable = false, length = 200)
    private String venue;
    @Min(1) @Column(nullable = false)
    private int capacity;
    @Column(name = "registration_open", nullable = false)
    private boolean registrationOpen = true;

    public Event() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public LocalDate getEventDate(){return eventDate;} public void setEventDate(LocalDate eventDate){this.eventDate=eventDate;}
    public LocalTime getEventTime(){return eventTime;} public void setEventTime(LocalTime eventTime){this.eventTime=eventTime;}
    public String getVenue(){return venue;} public void setVenue(String venue){this.venue=venue;}
    public int getCapacity(){return capacity;} public void setCapacity(int capacity){this.capacity=capacity;}
    public boolean isRegistrationOpen(){return registrationOpen;} public void setRegistrationOpen(boolean registrationOpen){this.registrationOpen=registrationOpen;}
}
