package pdd.test.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import pdd.test.domain.PersonTest;
import pdd.test.domain.PersonTestQuestion;
import pdd.test.domain.Question;
import pdd.test.domain.QuestionData;
import pdd.test.repository.PersonTestRepository;
import pdd.test.telegram.domain.Messages;
import pdd.test.telegram.handlers.TelegramCommand;

import java.io.ByteArrayInputStream;

import static pdd.test.classifiers.DataType.IMAGE;

@Service
@RequiredArgsConstructor
public class QuestionHandlerService {
    private final PersonTestRepository personTestRepository;

    @Transactional(readOnly = true)
    public Messages handleQuestion(Long personTestId, long chatId) {
        Messages messages = new Messages();
        PersonTest personTest = personTestRepository.findById(personTestId).get();
        PersonTestQuestion testQuestion = personTest.getTestQuestions().stream()
                .filter(PersonTestQuestion::notExistAnswer)
                .findFirst()
                .orElse(null);

        if (testQuestion == null) {
            return messages;
        }

        Question question = testQuestion.getQuestion();

        if (question.getQuestionData() != null) {
            QuestionData questionData = question.getQuestionData();
            if (IMAGE.is(questionData.getType())) {
                InputFile file = new InputFile();
                file.setMedia(new ByteArrayInputStream(questionData.getData()), "question");
                SendPhoto photoMessage = SendPhoto.builder()
                        .chatId(chatId)
                        .photo(file)
                        .caption(question.getText())
                        .replyMarkup(getAnswerOptions(testQuestion))
                        .build();
               messages.add(photoMessage);
            }
        } else {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(question.getText())
                    .replyMarkup(getAnswerOptions(testQuestion))
                    .build();
            messages.add(message);
        }

        return messages;
    }

    private InlineKeyboardMarkup getAnswerOptions(PersonTestQuestion testQuestion) {
        return new InlineKeyboardMarkup(testQuestion.getQuestion().getAnswers().stream()
                .map(answer -> new InlineKeyboardRow(
                        InlineKeyboardButton.builder()
                                .text(answer.getDescription())
                                .callbackData(TelegramCommand.formAnswerActionData(testQuestion.getId(), answer.getId()))
                                .build()
                ))
                .toList()
        );
    }

}
