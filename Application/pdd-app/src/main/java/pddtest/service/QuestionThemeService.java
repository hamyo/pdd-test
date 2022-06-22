package pddtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pddtest.model.QuestionTheme;

import javax.annotation.Resource;

@Service
public class QuestionThemeService {
    @Resource QuestionThemeService questionThemeRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    void save(QuestionTheme theme) {
        questionThemeRepository.save(theme);
    }

    @Transactional(readOnly = true)
    Iterable<QuestionTheme> findAll() {
        return questionThemeRepository.findAll();
    }
}
