package ge.temojudo.digitalauction.entity.auctions;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "auctions")
public class AuctionEntity {

    @Id
    @SequenceGenerator(
            name = "auctions_seq",
            sequenceName = "auctions_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "auctions_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    private String title;

    @Column(length = 4095)
    private String description;

    private String photoId;

    private Date creationDate;

    private Date startDate;

    private String status;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private UserEntity registrationUser;

    public AuctionEntity(
            String title,
            String description,
            String photoId,
            Date creationDate,
            Date startDate,
            String status,
            UserEntity registrationUser
    ) {
        this.title = title;
        this.description = description;
        this.photoId = photoId;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.status = status;
        this.registrationUser = registrationUser;
    }

}
