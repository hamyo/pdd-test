package pdd.test.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdd.test.classifiers.Role;
import pdd.test.domain.ClsRole;
import pdd.test.domain.Person;
import pdd.test.repository.PersonRepository;
import pdd.test.utils.BusinessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person getPersonByTelegramId(long telegramUserId) {
        return personRepository.getPersonByTelegramId(telegramUserId);
    }

    public boolean isPersonByTelegramIdExists(Long telegramUserId) {
        if (telegramUserId == null) {
            return false;
        }

        return personRepository.getPersonByTelegramId(telegramUserId) != null;
    }

    public boolean isPersonAdmin(Long telegramUserId) {
        if (telegramUserId == null) {
            return false;
        }

        Person person = personRepository.getPersonByTelegramId(telegramUserId);
        return person != null && person.isAdmin();
    }

    @Transactional(readOnly = true)
    public Optional<Person> findActivePersonByName(String lastname, String name) {
        List<Person> persons = Collections.emptyList();
        if (StringUtils.isNotBlank(lastname)) {
            persons = personRepository.getPersonsByActiveAndLastnameIgnoreCase(true, lastname);
            if (StringUtils.isNotBlank(name) && !persons.isEmpty()) {
                persons = persons.stream()
                        .filter(person -> name.equalsIgnoreCase(person.getName()))
                        .toList();
            }
        }

        if (persons.isEmpty()) {
            throw new BusinessException("Не удалось определить пользователя");
        }

        if (persons.size() > 1) {
            throw new BusinessException("По указанной фамилии и имени найдено более одного активного пользователя");
        }

        return Optional.of(persons.getFirst());
    }

    @Transactional(readOnly = true)
    public List<Person> findActivePersons() {
        return personRepository.getPersonsByActive(true);
    }

    @Transactional
    public void inactivatePerson(Integer personId) {
        personRepository.findById(personId)
                .ifPresent(person -> person.setActive(false));
    }

    public void create(String lastName, String firstName, String patronymic, boolean isAdmin) {
        Person person = new Person();
        person.setLastname(lastName);
        person.setName(firstName);
        person.setPatronymic(patronymic);

        person.getRoles().add(new ClsRole(Role.USER));
        if (isAdmin) {
            person.getRoles().add(new ClsRole(Role.ADMIN));
        }
        personRepository.save(person);
    }
}
