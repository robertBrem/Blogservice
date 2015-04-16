package expert.optimist.blog.blogservice.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Entry {
    private String author;
    private String title;
    private String teaser;
    private String content;
}
