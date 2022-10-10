package com.bustle.bustlebackend.service;

import com.bustle.hackto2022.constants.BustleConstants;
import com.bustle.hackto2022.model.Person;
import com.bustle.hackto2022.model.Roles;
import com.bustle.hackto2022.repository.PersonRepository;
import com.bustle.hackto2022.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonService {

    // turn array of skills into string as function and save it into a function;
    // turn array of tech interest into string as function and save it into a function;
    // turn array of hobbies into string as function and save it into a function;
    // get buzzwords from reviews // will do and optimize later, rn just focus on populating buzzwords manually;
    // turn array of buzzwords into string as function and save it into a function;
    // turn array of workstyles into string as function and save it into a function;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(BustleConstants.CO_WORKER_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }

}
