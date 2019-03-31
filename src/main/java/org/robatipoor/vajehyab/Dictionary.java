package org.robatipoor.vajehyab;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dictionary
 */
public class Dictionary {

    private static final String VAJEH_YAB_URL = "https://www.vajehyab.com/?q=";
    private static final Logger LOG = LoggerFactory.getLogger(Dictionary.class);

    public static String search(String word) throws IOException {
        LOG.info("Search Word {}", word);
        String url = VAJEH_YAB_URL + word;
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        Elements secs = doc.select("div.sections").last().select("section");
        StringBuilder sb = new StringBuilder();
        for (Element sec : secs) {
            sb.append(sec.select("header").first().text());
            sb.append(" : ");
            sb.append(sec.select("p").first().text());
            sb.append("\n");
        }

        return sb.toString();
    }
}