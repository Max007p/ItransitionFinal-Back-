package entities;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Indexed
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creation;

    @Field(termVector = TermVector.YES)
    @Column(name = "text")
    private String text;

}
