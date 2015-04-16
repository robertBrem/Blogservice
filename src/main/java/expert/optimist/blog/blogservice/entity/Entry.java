package expert.optimist.blog.blogservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
}
