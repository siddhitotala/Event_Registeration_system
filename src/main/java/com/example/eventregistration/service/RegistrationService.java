package com.example.eventregistration.service;
import com.example.eventregistration.entity.Event;
import com.example.eventregistration.entity.Registration;
import com.example.eventregistration.repository.EventRepository;
import com.example.eventregistration.repository.RegistrationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class RegistrationService {
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    public RegistrationService(EventRepository e, RegistrationRepository r){this.eventRepository=e;this.registrationRepository=r;}

    @Transactional
    public Registration register(Long eventId, Registration request){
        Event event=eventRepository.findById(eventId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));
        if(!event.isRegistrationOpen()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Registration is closed");
        String email=request.getEmail().trim().toLowerCase();
        if(registrationRepository.existsByEventIdAndEmailIgnoreCase(eventId,email))
            throw new ResponseStatusException(HttpStatus.CONFLICT,"This email is already registered for the event");
        if(registrationRepository.countByEventId(eventId)>=event.getCapacity())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Event has reached capacity");
        request.setId(null); request.setEmail(email); request.setEvent(event);
        try { return registrationRepository.saveAndFlush(request); }
        catch(DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"A registration with this email already exists for the event");
        }
    }

    public List<Registration> findForEvent(Long eventId){
        if(!eventRepository.existsById(eventId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found");
        return registrationRepository.findByEventIdOrderByRegisteredAtDesc(eventId);
    }
}
