package org.robatipoor;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * BotHandler
 */
public abstract class BotHandler implements Route {

    @Override
    public Object handle(Request request, Response response) {
        Update update = BotUtils.parseUpdate(request.body());
        Message message = update.message();

        if (isStartMessage(message) && onStart(message)) {
            return "ok";
        } else {
            onWebhookUpdate(update);
        }
        return "ok";
    }

    private boolean isStartMessage(Message message) {
        return message != null && message.text() != null && message.text().startsWith("/start");
    }

    protected boolean onStart(Message message) {
        return false;
    }

    public abstract void onWebhookUpdate(Update update);

    public abstract String getToken();

    public abstract TelegramBot getBot();
}