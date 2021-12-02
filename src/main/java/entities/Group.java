package entities;

import enums.ReviewGroups;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true)
    private ReviewGroups groups;

    @ManyToMany(mappedBy = "reviewGroups")
    private List<Review> reviews;
}
