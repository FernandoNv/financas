package com.example.financas.controller;

import com.example.financas.dto.HttpErrorResponse;
import com.example.financas.dto.PersonDTO;
import com.example.financas.model.Person;
import com.example.financas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/persons")
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Person>> listPersons(){
        List<Person> persons = personService.findAll();

        return ResponseEntity.ok().body(persons);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> getPersonById(@PathVariable Long id){
        Person person = personService.findById(id);
        if(person == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().body(person);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody PersonDTO personDTO){
        Person newPerson = personService.addPerson(personDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPerson.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newPerson);
    }
    @PutMapping(value = "/{id}" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updatePerson(@Valid @RequestBody PersonDTO personDTO, @PathVariable Long id){
        Person updatedPerson = personService.updatePerson(personDTO, id);

        if(updatedPerson == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().body(updatedPerson);
    }

    @DeleteMapping(value = "/{id}" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id){
        boolean isDeleted = personService.deletePerson(id);

        if(!isDeleted){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().build();
    }
}
