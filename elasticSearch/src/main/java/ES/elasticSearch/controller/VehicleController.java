package ES.elasticSearch.controller;

import ES.elasticSearch.document.Person;
import ES.elasticSearch.document.Vehicle;
import ES.elasticSearch.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final IndexService service;

    @PostMapping
    public void save(@RequestBody final Vehicle vehicle ) {
        service.save(vehicle);
    }

    @GetMapping("{id}")
    public Vehicle getById(@PathVariable String id) {
        return service.getById(id);
    }


}
