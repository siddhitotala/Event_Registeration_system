package com.example.eventregistration.service;
import com.example.eventregistration.entity.Event;
import com.example.eventregistration.repository.EventRepository;
import com.example.eventregistration.repository.RegistrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    public EventService(EventRepository e, RegistrationRepository r){this.eventRepository=e;this.registrationRepository=r;}
    public List<Event> findAll(){return eventRepository.findAll();}
    public Event findById(Long id){return eventRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));}
    public Event create(Event event){event.setId(null);return eventRepository.save(event);}
    public Event update(Long id, Event incoming){
        Event current=findById(id);
        current.setName(incoming.getName()); current.setDescription(incoming.getDescription());
        current.setEventDate(incoming.getEventDate()); current.setEventTime(incoming.getEventTime());
        current.setVenue(incoming.getVenue()); current.setCapacity(incoming.getCapacity());
        current.setRegistrationOpen(incoming.isRegistrationOpen());
        if(registrationRepository.countByEventId(id)>current.getCapacity())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Capacity cannot be lower than existing registrations");
        return eventRepository.save(current);
    }
    public Event setRegistrationOpen(Long id, boolean open){
        Event e=findById(id);
        if(open && registrationRepository.countByEventId(id)>=e.getCapacity())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Event is already at capacity");
        e.setRegistrationOpen(open); return eventRepository.save(e);
    }
    public void delete(Long id){
        Event e=findById(id);
        if(registrationRepository.countByEventId(id)>0) throw new ResponseStatusException(HttpStatus.CONFLICT,"Cannot delete an event with registrations");
        eventRepository.delete(e);
    }
}
