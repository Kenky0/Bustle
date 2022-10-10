package com.bustle.bustlebackend.restcontroller;

import com.bustle.hackto2022.constants.BustleConstants;
import com.bustle.hackto2022.model.Response;
import com.bustle.hackto2022.model.Suggestion;
import com.bustle.hackto2022.repository.SuggestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/suggestion",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*") // @CrossOrigin(origins="http://localhost:8080/") // will allow api calls from/to on specified domain; USE LATTER FOR PROPER DEVELOPMENT CAUSE ORIGIN WILL BE PROVIDED BY DEV. OPS TEAM;

public class SuggestionRestController {

    @Autowired
    SuggestionRepository suggestionRepository;

    @GetMapping("/getSuggestionByStatus")
    @ResponseBody
    public List<Suggestion> getSuggestionsByStatus(@RequestParam(name = "status") String status) {
        return suggestionRepository.findByStatus(status);
    }

    @GetMapping("/getAllSuggestionsByStatus")
    @ResponseBody // sending this to the body;
    public List<Suggestion> getAllSuggestionsByStatus(@RequestBody Suggestion suggestion) {
        if (null != suggestion && null != suggestion.getStatus()) {
            return suggestionRepository.findByStatus(suggestion.getStatus());
        } else {
            return List.of();
        }
    }

    @PostMapping("/saveSuggestion")
    @ResponseBody
    public ResponseEntity<Response> saveSuggestion(@RequestHeader("invocationFrom") String invocationFrom,
                                                   @Valid @RequestBody Suggestion suggestion) { // response body allows you to club response body, header and status all into one thing to send to your rest service;
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        suggestionRepository.save(suggestion);
        Response response = new Response(); // If only used Response, will only get status response and status code in body, but because also want to send header info and status info, responseEntity is needed;
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response); // whatever you're sending here should also be what's inside ResponseEntity<Response>;
    }

    @DeleteMapping("/deleteSuggestion")
    @ResponseBody
    public ResponseEntity<Response> deleteSuggestion(RequestEntity<Suggestion> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Suggestion suggestion = requestEntity.getBody();
        suggestionRepository.deleteById(suggestion.getSuggestionId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeSuggestion")
    @ResponseBody
    public ResponseEntity<Response> closeSuggestions(@RequestBody Suggestion suggestionReq) { // updating a message from open to close;
        Response response = new Response();
        Optional<Suggestion> suggestion = suggestionRepository.findById(suggestionReq.getSuggestionId());
        if (suggestion.isPresent()) {
            suggestion.get().setStatus(BustleConstants.CLOSE);
            suggestionRepository.save(suggestion.get());
        } else {
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
