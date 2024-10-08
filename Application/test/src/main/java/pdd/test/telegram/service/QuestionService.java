package pdd.test.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.*;
import pdd.test.telegram.handlers.TelegramCommand;

import static pdd.test.classifiers.DataType.IMAGE;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final TelegramClient telegramClient;

    @SneakyThrows
    @Transactional(readOnly = true)
    public void handleQuestion(PersonTestQuestion testQuestion, long chatId) {
        Question question = testQuestion.getQuestion();

        if (question.getQuestionData() != null) {
            QuestionData questionData = question.getQuestionData();
            if (IMAGE.is(questionData.getType())) {
                SendPhoto photoMessage = SendPhoto.builder()
                        .chatId(chatId)
                        .caption(question.getText())
                        .replyMarkup(getAnswerOptions(testQuestion))
                        .build();
                telegramClient.execute(photoMessage);
            }
        } else {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(question.getText())
                    .replyMarkup(getAnswerOptions(testQuestion))
                    .build();
            telegramClient.execute(message);
        }
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
