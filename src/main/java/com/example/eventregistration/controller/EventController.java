package com.example.eventregistration.controller;
import com.example.eventregistration.entity.Event;
import com.example.eventregistration.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService){this.eventService=eventService;}
    @GetMapping public List<Event> allEvents(){return eventService.findAll();}
    @GetMapping("/{id}") public Event getEvent(@PathVariable Long id){return eventService.findById(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @RequestBody Event event){return eventService.create(event);}
    @PutMapping("/{id}") public Event updateEvent(@PathVariable Long id,@Valid @RequestBody Event event){return eventService.update(id,event);}
    @PatchMapping("/{id}/registration") public Event setRegistration(@PathVariable Long id,@RequestParam boolean open){return eventService.setRegistrationOpen(id,open);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteEvent(@PathVariable Long id){eventService.delete(id);}
}
