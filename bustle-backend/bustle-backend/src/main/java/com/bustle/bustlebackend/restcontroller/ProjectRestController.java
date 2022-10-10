package com.bustle.bustlebackend.restcontroller;

import com.bustle.hackto2022.constants.BustleConstants;
import com.bustle.hackto2022.model.Project;
import com.bustle.hackto2022.model.Response;
import com.bustle.hackto2022.model.Suggestion;
import com.bustle.hackto2022.repository.ProjectRepository;
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
@RequestMapping(path = "/api/project",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*") // @CrossOrigin(origins="http://localhost:8080/") // will allow api calls from/to on specified domain; USE LATTER FOR PROPER DEVELOPMENT CAUSE ORIGIN WILL BE PROVIDED BY DEV. OPS TEAM;
public class ProjectRestController {
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/getProjectByStatus")
    @ResponseBody
    public List<Project> getProjectByStatus(@RequestParam(name = "status") String status) {
        return projectRepository.findProjectByStatus(status);
    }

    @GetMapping("/getAllProjectsByStatus")
    @ResponseBody // sending this to the body;
    public List<Project> getAllProjectByStatus(@RequestBody Project project) {
        if (null != project && null != project.getStatus()) {
            return projectRepository.findProjectByStatus(project.getStatus());
        } else {
            return List.of();
        }
    }

    @PostMapping("/saveProject")
    @ResponseBody
    public ResponseEntity<Response> saveProject(@RequestHeader("invocationFrom") String invocationFrom,
                                                @Valid @RequestBody Project project) { // response body allows you to club response body, header and status all into one thing to send to your rest service;
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        projectRepository.save(project);
        Response response = new Response(); // If only used Response, will only get status response and status code in body, but because also want to send header info and status info, responseEntity is needed;
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response); // whatever you're sending here should also be what's inside ResponseEntity<Response>;
    }

    @DeleteMapping("/deleteProject")
    @ResponseBody
    public ResponseEntity<Response> deleteProject(RequestEntity<Project> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Project project = requestEntity.getBody();
        projectRepository.deleteById(project.getProjectId());
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
        Optional<Project> project = projectRepository.findById(suggestionReq.getSuggestionId());
        if (project.isPresent()) {
            project.get().setStatus(BustleConstants.CLOSE);
            projectRepository.save(project.get());
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
