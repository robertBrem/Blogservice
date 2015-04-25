package expert.optimist.blog.comment.entity;

import expert.optimist.blog.LocalDateTimeAdapter;
import expert.optimist.blog.entry.entity.Entry;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "COMMENTS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@NamedQueries(
        @NamedQuery(name = "Comments.getAll", query = "SELECT DISTINCT c FROM Comment c")
)
public class Comment {
    @Id
    @SequenceGenerator(name = "CommentsSequence", sequenceName = "COMMENTS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommentsSequence")
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(unique = true)
    private String urlTitle;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "ENTRY_ID", nullable = false)
    private Entry entry;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @XmlTransient
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private Set<Comment> children;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDate;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime modDate;

    private String modUser;
}
