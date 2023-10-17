package ru.kovrizhkin.kovrizhka_dev_bot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kovrizhkin.kovrizhka_dev_bot.config.DevBotConfig;

@Component
public class DevBot extends TelegramLongPollingBot {

    final DevBotConfig devBotConfig;

    public DevBot(DevBotConfig devBotConfig) {
        this.devBotConfig = devBotConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default: sendMessage(chatId, "Такой команды пока нет");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {

        String answer = "Привет, " + name + "!";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {

        }

    }

    @Override
    public String getBotUsername() {
        return "kovrizhka_dev_bot";
    }

    public String getBotToken() {
        return devBotConfig.getToken();
    }
}
