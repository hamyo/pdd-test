package pdd.test.service;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdd.test.domain.Person;
import pdd.test.repository.PersonRepository;
import pdd.test.utils.BusinessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Resource private PersonRepository personRepository;

    public boolean isPersonByTelegramIdExists(Long telegramUserId) {
        if (telegramUserId == null) {
            return false;
        }

        return personRepository.getPersonByTelegramId(telegramUserId) != null;
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
}
