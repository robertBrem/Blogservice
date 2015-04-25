package expert.optimist.blog.entry.entity;

import expert.optimist.blog.LocalDateTimeAdapter;
import expert.optimist.blog.comment.entity.Comment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ENTRIES")
@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@NamedQueries({
        @NamedQuery(name = "Entries.getAll", query = "SELECT DISTINCT e FROM Entry e LEFT JOIN FETCH e.comments"),
        @NamedQuery(name = "Entries.findById", query = "SELECT DISTINCT e FROM Entry e LEFT JOIN FETCH e.comments WHERE e.id = :id")
})
public class Entry {
    @Id
    @SequenceGenerator(name = "EntriesSequence", sequenceName = "ENTRIES_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EntriesSequence")
    @Column(name = "ENTRY_ID")
    private Long id;

    @Column(unique = true)
    private String urlTitle;

    private String author;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String teaser;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords;

    @OneToMany(mappedBy = "entry", orphanRemoval = true)
    private Set<Comment> comments;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDate;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime modDate;

    private String modUser;
}
