package org.robatipoor;

import com.pengrad.telegrambot.request.SetWebhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.robatipoor.removelink.*;
import org.robatipoor.vajehyab.*;

import static spark.Spark.*;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final String PORT = System.getenv("PORT");
    private static final String APP_URL = System.getenv("APP_URL");

    public static void main(String[] args) {

        if (APP_URL != null) {
            LOG.info("Application Url = {}", APP_URL);
        }
        if (PORT != null) {
            port(Integer.parseInt(PORT));
            LOG.info("Port Number = {}", PORT);
        }
        // define list of bots
        BotHandler[] bots = new BotHandler[] { new RemoveLinkTelegramBot(), new VajehYabTelegramBot() };
        // register this URL as Telegram Webhook
        for (BotHandler bot : bots) {
            String token = bot.getToken();
            LOG.info("Telegram Token = {}", token);
            post("/" + token, bot);
            if (APP_URL != null) {
                String url = APP_URL + "/" + token;
                LOG.info("Url Bot = {}", url);
                bot.getBot().execute(new SetWebhook().url(url));
            }
        }

        get("/", (req, res) -> "Bot @nameBot is live !");
        post("/", (req, res) -> "Bot @nameBot is live !");
    }
}
