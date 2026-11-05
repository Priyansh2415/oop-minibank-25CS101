import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {

    static String fill(String template, String[] names, String[] values) {
        // (b) pattern for {placeholder}
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher m = pattern.matcher(template);

        // (c) walk the matches and build the result
        StringBuilder sb = new StringBuilder();
        int last = 0;
        while (m.find()) {
            sb.append(template, last, m.start());
            String key = m.group(1);
            String replacement = "[?]";
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(key)) {
                    replacement = values[i];
                    break;
                }
            }
            sb.append(replacement);
            last = m.end();
        }
        sb.append(template.substring(last));
        return sb.toString();
    }
}
