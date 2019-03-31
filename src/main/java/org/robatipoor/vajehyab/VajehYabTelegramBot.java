package org.robatipoor.vajehyab;

import java.io.IOException;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import org.robatipoor.BotHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VajehYabTelegramBot
 */
//
public class VajehYabTelegramBot extends BotHandler {

    private static final Logger LOG = LoggerFactory.getLogger(VajehYabTelegramBot.class);
    private static final String TOKEN = System.getenv("TELEGRAM_TOKEN_VAJEH");
    private final TelegramBot bot = new TelegramBot(TOKEN);

    @Override
    public void onWebhookUpdate(Update update) {
        Long chatId = update.message().chat().id();
        LOG.info("Get Update Chat ID = {}", chatId);
        String word = update.message().text();
        if (word != null) {
            try {
                String mean = Dictionary.search(word);
                bot.execute(new SendMessage(chatId, mean));
            } catch (IOException e) {
                LOG.warn(e.toString());
            }
        }else{
            bot.execute(new SendMessage(chatId, "Please send me farsi word !"));
        }
    }

    @Override
    public String getToken() {
        return TOKEN;
    }

    @Override
    public TelegramBot getBot() {
        return this.bot;
    }
}