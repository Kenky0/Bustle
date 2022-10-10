package com.bustle.bustlebackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventService {

//    PersonRepository personRepository;
//    @Autowired
//    private EventsRepository eventsRepository;
//
//    @Autowired
//    private RolesRepository rolesRepository;
//
//    public boolean createNewEvent(BustleConstants status){
//        boolean isSaved = false;
//        EventPost eventPost = eventsRepository.getByEventId(status);
//        person.setRoles(eventPost);
//        person.setPwd(passwordEncoder.encode(person.getPwd()));
//        person = eventsRepository.save(EventPost);
//        if (null != person && person.getPersonId() > 0)
//        {
//            isSaved = true;
//        }
//        return isSaved;
//    }
//
//    public boolean changeEventState(int id) {
//        boolean isSaved = false;
//        EventPost eventPost = eventsRepository.getByEventId(id);
//        eventPost.setStatus(BustleConstants.HOSTED);
//        eventsRepository.save(eventPost);
//        return isSaved;
//    }
}
