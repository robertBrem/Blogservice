package expert.optimist.blog.blogservice.entity;

import expert.optimist.blog.blogservice.LocalDateTimeAdapter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENTRIES")
@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@NamedQueries(
        @NamedQuery(name = "Entries.getAll", query = "SELECT e FROM Entry e")
)
public class Entry {
    @Id
    @SequenceGenerator(name = "EntriesSequence", sequenceName = "ENTRIES_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EntriesSequence")
    @Column(name = "ENTRY_ID")
    private Long id;
    private String author;
    private String title;
    private String teaser;
    private String content;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDate;
}
