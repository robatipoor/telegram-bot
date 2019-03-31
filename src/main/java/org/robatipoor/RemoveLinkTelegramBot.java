package org.robatipoor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendVideo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RemoveLinkTelegramBot
 */
public class RemoveLinkTelegramBot extends BotHandler {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final String TOKEN = System.getenv("TELEGRAM_TOKEN_QUEEN");
    private final TelegramBot bot = new TelegramBot(TOKEN);

    @Override
    void onWebhookUpdate(Update update) {
        Long chatId = update.message().chat().id();
        LOG.info("Get Update Chat ID = {}", chatId);
        String caption = update.message().caption();
        if (caption != null) {
            caption = removeLinkText(caption);
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
        } else if (update.message().text() != null) {
            var text = update.message().text();
            LOG.info("Get Text {} ", text);
            bot.execute(new SendMessage(chatId, removeLinkText(text)));
        }
    }

    @Override
    String getToken() {
        return TOKEN;
    }

    @Override
    TelegramBot getBot() {
        return bot;
    }

    private static String removeLinkText(String text) {
        String result = text;
        String[] regexs = { "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                "\\b(www?|WWW|WWw).[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                "\\b(telegram\\.me)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                "\\b(t\\.me/)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                "\\b(\\@)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "(@)\\w+" };

        for (String re : regexs) {
            Matcher match = Pattern.compile(re).matcher(result);
            while (match.find()) {
                result = result.replace(match.group(), "");
                LOG.info("Remove {} ", match.group());
            }
        }
        return result;
    }
}