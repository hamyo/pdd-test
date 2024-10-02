package pdd.test.telegram.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class Config {
    @Value("${telegram.bot.token}") private String token;
    @Value("${telegram.bot.name}") private String name;

    @Bean("telegramClient")
    public TelegramClient getTelegramClient() {
        return new OkHttpTelegramClient(token);
    }
}
