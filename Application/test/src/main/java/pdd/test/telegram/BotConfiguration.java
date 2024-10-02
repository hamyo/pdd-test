package pdd.test.telegram;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfiguration {
    @Value("${telegram.bot.token}") private String token;
    @Value("${telegram.bot.name}") private String name;
}
