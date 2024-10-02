package pdd.test.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.Person;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person getPersonByTelegramId(long telegramId);
    List<Person> getPersonsByActiveAndLastnameIgnoreCase(boolean active, String lastname);
}
