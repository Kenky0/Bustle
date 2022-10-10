package com.bustle.bustlebackend.restcontroller;

import com.bustle.hackto2022.model.Person;
import com.bustle.hackto2022.model.Response;
import com.bustle.hackto2022.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/project",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*") // @CrossOrigin(origins="http://localhost:8080/") // will allow api calls from/to on specified domain; USE LATTER FOR PROPER DEVELOPMENT CAUSE ORIGIN WILL BE PROVIDED BY DEV. OPS TEAM;
public class PersonRestController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/getPersonById")
    @ResponseBody
    public Optional<Person> getPersonById(@RequestParam(name = "person_Id") Integer id) {
        return personRepository.findById(id);
    }

//    @GetMapping("/getAllSuggestionsByStatus")
//    @ResponseBody // sending this to the body;
//    public List<Person> getAllSuggestionsByStatus(@RequestBody Suggestion suggestion) {
//        if (null != suggestion && null != suggestion.getStatus()) {
//            return suggestionRepository.findByStatus(suggestion.getStatus());
//        } else {
//            return List.of();
//        }
//    }

    @PostMapping("/savePerson")
    @ResponseBody
    public ResponseEntity<Response> savePerson(@RequestHeader("invocationFrom") String invocationFrom,
                                               @Valid @RequestBody Person person) { // response body allows you to club response body, header and status all into one thing to send to your rest service;
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        personRepository.save(person);
        Response response = new Response(); // If only used Response, will only get status response and status code in body, but because also want to send header info and status info, responseEntity is needed;
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response); // whatever you're sending here should also be what's inside ResponseEntity<Response>;
    }

    @DeleteMapping("/deletePerson")
    @ResponseBody
    public ResponseEntity<Response> deletePerson(RequestEntity<Person> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Person person = requestEntity.getBody();
        personRepository.deleteById(person.getPersonId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

//    @PatchMapping("/closePerson")
//    @ResponseBody
//    public ResponseEntity<Response> closePersons(@RequestBody Person personReq) { // updating a message from open to close;
//        Response response = new Response();
//        Optional<Person> person = personRepository.findById(personReq.getPersonId());
//        if (person.isPresent()) {
//            person.get().setStatus(BustleConstants.CLOSE);
//            personRepository.save(person.get());
//        } else {
//            response.setStatusCode("400");
//            response.setStatusMsg("Invalid Contact ID received");
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }
//        response.setStatusCode("200");
//        response.setStatusMsg("Message successfully closed");
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//
//    }
}
