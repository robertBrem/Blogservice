package expert.optimist.blog;


import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class TextConverter {

    public static final String URL_ENTRY_SEPARATOR = "__";

    public String toUrlString(String text) {
        return text.replaceAll("\\s", "_");
    }

    public String toUrlString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm_ss"));
    }

    public String getUrlTitle(String author, LocalDateTime dateTime) {
        return toUrlString(author) + URL_ENTRY_SEPARATOR + toUrlString(dateTime);
    }

    public String getUrlTitle(String title, String author, LocalDateTime dateTime) {
        return toUrlString(title) + URL_ENTRY_SEPARATOR + getUrlTitle(author, dateTime);
    }

}
