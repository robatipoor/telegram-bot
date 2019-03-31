package org.robatipoor.removelink;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.request.SendVoice;

import org.robatipoor.BotHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RemoveLinkTelegramBot
 */
public class RemoveLinkTelegramBot extends BotHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RemoveLinkTelegramBot.class);
    private static final String TOKEN = System.getenv("TELEGRAM_TOKEN_QUEEN");
    private final TelegramBot bot = new TelegramBot(TOKEN);

    @Override
    public void onWebhookUpdate(Update update) {
        Long chatId = update.message().chat().id();
        LOG.info("Get Update Chat ID = {}", chatId);
        String caption = update.message().caption();
        if (caption != null) {
            caption = RemoveLink.remove(caption);
        }
        if (update.message().video() != null) {
            var fileId = update.message().video().fileId();
            LOG.info("Get Video ID {}", fileId);
            bot.execute(new SendVideo(chatId, fileId).caption(caption));
        } else if (update.message().document() != null) {
            var fileId = update.message().document().fileId();
            LOG.info("Get Document ID {}", fileId);
            bot.execute(new SendDocument(chatId, fileId).caption(caption));
        } else if (update.message().photo() != null) {
            if (update.message().photo().length >= 1) {
                var fileId = update.message().photo()[0].fileId();
                LOG.info("Get Photo ID {} ", fileId);
                bot.execute(new SendPhoto(chatId, fileId).caption(caption));
            }
        } else if (update.message().audio() != null) {
            var fileId = update.message().audio().fileId();
            LOG.info("Get Audio ID {}", fileId);
            bot.execute(new SendAudio(chatId, fileId).caption(caption));
        } else if (update.message().voice() != null) {
            var fileId = update.message().voice().fileId();
            LOG.info("Get Voice ID {}", fileId);
            bot.execute(new SendVoice(chatId, fileId).caption(caption));
        } else if (update.message().text() != null) {
            var text = update.message().text();
            LOG.info("Get Text {} ", text);
            bot.execute(new SendMessage(chatId, RemoveLink.remove(text)));
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