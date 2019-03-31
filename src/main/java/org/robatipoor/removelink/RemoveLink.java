package org.robatipoor.removelink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RemoveLink
 */
public class RemoveLink {

    private static final Logger LOG = LoggerFactory.getLogger(RemoveLink.class);
    private static final String[] REGEXS = { "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
    "\\b(www?|WWW|WWw).[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
    "\\b(telegram\\.me)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
    "\\b(t\\.me/)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
    "\\b(\\@)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "(@)\\w+" };

    public static String remove(String text) {
        String result = text;
        for (String re : REGEXS) {
            Matcher match = Pattern.compile(re).matcher(result);
            while (match.find()) {
                result = result.replace(match.group(), "");
                LOG.info("Remove {} ", match.group());
            }
        }
        return result;
    }
}