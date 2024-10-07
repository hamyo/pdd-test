package pdd.test.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdd.test.domain.AvailableTest;
import pdd.test.domain.AvailableTestTheme;
import pdd.test.domain.Person;
import pdd.test.domain.Question;

import java.util.List;

@Service
public class TestService {

    @Transactional
    public void createPersonTest(@NonNull Long personId, @NonNull Integer avalaibleTestId) {
        Person person = new Person();
        AvailableTest availableTest = new AvailableTest();

        availableTest.getTestThemes().stream()
                .flatMap(testTheme -> )
    }

    private List<Question> formQuestions(@NonNull AvailableTestTheme testTheme) {

    }
}
