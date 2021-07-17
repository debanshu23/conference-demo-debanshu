package com.capgemini.conference.controllers;

import com.capgemini.conference.models.Session;
import com.capgemini.conference.models.Speaker;
import com.capgemini.conference.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list(){
        final List<Session> sessions = sessionRepository.findAll();
        if(!sessions.isEmpty()){
            return sessions;
        }

        Session session = new Session();
        session.setSession_id(0L);
        session.setSession_name("Not found");
        session.setSession_length(120);
        session.setSpeakers(new ArrayList<Speaker>());

        List<Session> emptySession = new ArrayList<>();
        emptySession.add(session);
        return emptySession;
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //successful response status can be made dev defined
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

//    @DeleteMapping
//    @RequestMapping(value = "{id}")
    @RequestMapping(value = "{id}" , method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value= "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        Session existingSession = sessionRepository.getById(id);
        //BeanUtils is ready made class of Spring framework.
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }

}
