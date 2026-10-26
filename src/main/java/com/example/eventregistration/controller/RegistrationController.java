package com.example.eventregistration.controller;
import com.example.eventregistration.entity.Registration;
import com.example.eventregistration.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private final RegistrationService registrationService;
    public RegistrationController(RegistrationService registrationService){this.registrationService=registrationService;}
    @PostMapping("/events/{eventId}/registrations") @ResponseStatus(HttpStatus.CREATED)
    public Registration register(@PathVariable Long eventId,@Valid @RequestBody Registration request){return registrationService.register(eventId,request);}
    @GetMapping("/events/{eventId}/registrations")
    public List<Registration> registrationsForEvent(@PathVariable Long eventId){return registrationService.findForEvent(eventId);}
}
