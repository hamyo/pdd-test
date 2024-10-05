package pdd.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.Person;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person getPersonByTelegramId(long telegramUserId);
    List<Person> getPersonsByActiveAndLastnameIgnoreCase(boolean active, String lastname);
}
