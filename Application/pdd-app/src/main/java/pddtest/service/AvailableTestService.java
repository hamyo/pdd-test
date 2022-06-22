package pddtest.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pddtest.dao.AvailableTestRepository;
import pddtest.dao.QuestionThemeRepository;
import pddtest.model.AvailableTest;
import pddtest.model.QuestionTheme;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailableTestService {
    @Resource AvailableTestRepository availableTestRepository;
    @Resource QuestionThemeRepository questionThemeRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    void save(@NonNull AvailableTest inAvailableTest) {
        AvailableTest availableTest = inAvailableTest;
        if (!inAvailableTest.isNew()) {
            availableTest = availableTestRepository.findById(inAvailableTest.getId()).get();
            availableTest.setDescription(inAvailableTest.getDescription());
            availableTest.setName(inAvailableTest.getName());
            availableTest.setDuration(inAvailableTest.getDuration());
            availableTest.setPossibleErrors(inAvailableTest.getPossibleErrors());
            availableTest.getQuestionThemes().clear();
            availableTest.getQuestionThemes().addAll(inAvailableTest.getQuestionThemes());
        }
    }

    @Transactional(readOnly = true)
    Iterable<AvailableTest> findAll() {
        return availableTestRepository.findAll();
    }
}
