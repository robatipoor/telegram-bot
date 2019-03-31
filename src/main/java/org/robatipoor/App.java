package org.robatipoor;

import com.pengrad.telegrambot.request.SetWebhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final String PORT = System.getenv("PORT");
    private static final String APP_URL = System.getenv("APP_URL");

    public static void main(String[] args) {

        LOG.info("Application Url = {}", APP_URL);
        LOG.info("Port Number = {}", PORT);
        if (PORT != null) {
            port(Integer.parseInt(PORT));
        }
        // define list of bots
        BotHandler[] bots = new BotHandler[] { new RemoveLinkTelegramBot() };
        // register this URL as Telegram Webhook
        for (BotHandler bot : bots) {
            String token = bot.getToken();
            LOG.info("Telegram Token = {}", token);
            post("/" + token, bot);
            if (APP_URL != null) {
                String url = APP_URL + "/" + token;
                LOG.info("url = {}", token);
                bot.getBot().execute(new SetWebhook().url(url));
            }
        }

        get("/", (req, res) -> "Bot @nameBot Start !");
    }
}
