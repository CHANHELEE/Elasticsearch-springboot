package ES.elasticSearch.service;

import ES.elasticSearch.document.Person;
import ES.elasticSearch.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public  void save(final Person person){
        repository.save(person);
    }

    public Person findById(final String id){
        return  repository.findById(id).orElse(null);
    }


    public void getName(String name){

    }
}
