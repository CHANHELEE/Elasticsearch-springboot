package ES.elasticSearch.controller;

import ES.elasticSearch.document.Person;
import ES.elasticSearch.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService service;

    @Autowired
    public  PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public void save (@RequestBody final Person person) {
        service.save(person);
    }

    @GetMapping("/{id}")
    public  Person findById(@PathVariable  final String id) {

        return service.findById(id);
    }

    

}
