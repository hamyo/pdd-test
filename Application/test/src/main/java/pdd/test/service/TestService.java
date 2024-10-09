package pdd.test.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdd.test.domain.*;
import pdd.test.question.QuestionHandler;
import pdd.test.repository.PersonRepository;
import pdd.test.repository.PersonTestQuestionRepository;
import pdd.test.repository.PersonTestRepository;
import pdd.test.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestService {
    private final QuestionRepository questionRepository;
    private final PersonTestRepository personTestRepository;
    private final PersonTestQuestionRepository personTestQuestionRepository;
    private final List<QuestionHandler> questionHandlers;

    @Transactional
    public PersonTest createPersonTest(@NonNull Integer personId, @NonNull Integer avalaibleTestId) {
        Person person = new Person(personId);
        AvailableTest availableTest = new AvailableTest(avalaibleTestId);
        PersonTest personTest = new PersonTest();
        personTest.setPerson(person);
        personTest.setAvailableTest(availableTest);

        List<PersonTestQuestion> testQuestions = availableTest.getTestThemes().stream()
                .flatMap(testTheme -> getQuestionIds(testTheme).stream().sorted(Comparator.naturalOrder()))
                .map(id -> new PersonTestQuestion(new Question(id)))
                .toList();

        personTest.getTestQuestions().addAll(testQuestions);
        personTestRepository.save(personTest);
        return personTest;
    }

    @Transactional
    public PersonTestQuestion saveAnswer(Integer personTestQuestionId, String answerValue) {
        PersonTestQuestion testQuestion = personTestQuestionRepository.findById(personTestQuestionId).get();
        questionHandlers.stream()
                .filter(handler -> handler.canHandle(testQuestion.getQuestion().getType()))
                .findFirst()
                .ifPresent(handler -> handler.handle(testQuestion, answerValue));
        PersonTest personTest = testQuestion.getPersonTest();
        if (Boolean.TRUE.equals(testQuestion.getIsCorrect())) {
            personTest.setSuccess((short) (personTest.getSuccess() + 1));
        } else {
            personTest.setError((short) (personTest.getError() + 1));
            AvailableTest availableTest = personTest.getAvailableTest();
            if (availableTest.getMaxError() != null &&
                    availableTest.getMaxError().compareTo(personTest.getError()) <= 0) {
                personTest.setFinishDate(LocalDateTime.now());
            }
        }

        if (personTest.isNotFinished() &&
                personTest.getTestQuestions().stream().noneMatch(question -> question.getIsCorrect() == null)) {
            personTest.setFinishDate(LocalDateTime.now());
        }

        return testQuestion;
    }

    @NotNull
    private List<Long> getQuestionIds(@NonNull AvailableTestTheme testTheme) {
        List<Long> questionIds = questionRepository.getQuestionIdsByTheme(testTheme.getQuestionTheme().getId());
        if (questionIds.size() <= testTheme.getQuestionCount()) {
            return questionIds;
        }

        TreeSet<Long> selectedQuestionIds = new TreeSet<>();
        Random random = new Random();
        short repeatCount = 0;
        long minValueForSelected = 0;
        while (selectedQuestionIds.size() < testTheme.getQuestionCount()) {
            Long nextVal = random.nextLong(0, questionIds.size());
            if (selectedQuestionIds.contains(nextVal)) {
                if (repeatCount == 3) {
                    while (selectedQuestionIds.contains(minValueForSelected)) {
                        minValueForSelected++;
                    }

                    selectedQuestionIds.add(minValueForSelected);
                    repeatCount = 0;
                    minValueForSelected++;
                } else {
                    repeatCount++;
                }
            } else {
                repeatCount = 0;
                selectedQuestionIds.add(nextVal);
            }
        }

        return new ArrayList<>(selectedQuestionIds);
    }
}
