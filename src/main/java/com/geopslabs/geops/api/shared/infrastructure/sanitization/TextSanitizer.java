package com.geopslabs.geops.api.shared.infrastructure.sanitization;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.DOTALL;

public final class TextSanitizer {

    private static final Pattern SCRIPT_TAG       = Pattern.compile("<script[^>]*>.*?</script>", CASE_INSENSITIVE | DOTALL);
    private static final Pattern HTML_TAG         = Pattern.compile("<[^>]+>");
    private static final Pattern EVENT_HANDLERS   = Pattern.compile("\\bon\\w+\\s*=", CASE_INSENSITIVE);
    private static final Pattern JAVASCRIPT_PROTO = Pattern.compile("javascript\\s*:", CASE_INSENSITIVE);

    private TextSanitizer() {}

    public static String sanitize(String input) {
        if (input == null) return null;
        String result = SCRIPT_TAG.matcher(input).replaceAll("");
        result = EVENT_HANDLERS.matcher(result).replaceAll("");
        result = JAVASCRIPT_PROTO.matcher(result).replaceAll("");
        result = HTML_TAG.matcher(result).replaceAll("");
        return result.strip();
    }
}
