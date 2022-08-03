package com.example.financas.service;

import com.example.financas.dto.CategoryDTO;
import com.example.financas.dto.PersonDTO;
import com.example.financas.model.Category;
import com.example.financas.model.Person;
import com.example.financas.repository.PersonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO toDTO(@NotNull Person person){
        PersonDTO personDTO = new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getCreatedAt());
        personDTO.setPhoneNumbers(person.getPhoneNumbers());

        return personDTO;
    }

    public Person toPerson(@NotNull PersonDTO personDTO){
        Person person = new Person(personDTO.getId(), personDTO.getName(), personDTO.getEmail(), personDTO.getCreatedAt());
        person.setPhoneNumbers(personDTO.getPhoneNumbers());

        return person;
    }

    public Person addPerson(PersonDTO personDTO){
        Person person = toPerson(personDTO);
        person.setId(null);
        person.setCreatedAt(LocalDate.now());

        return personRepository.save(person);
    }

    public Person findById(Long id){
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person updatePerson(PersonDTO personDTO, Long id){
        Person personUpdate = toPerson(personDTO);
        Person person = findById(id);
        if(findById(id) != null){
            person.setName(personUpdate.getName());
            person.setPhoneNumbers(personUpdate.getPhoneNumbers());
            return personRepository.save(person);
        }

        return null;
    }

    public boolean deletePerson(Long id){
        Person person = findById(id);
        if(person != null){
            personRepository.deleteById(id);
            return true;
        }

        return false;
    }


}
