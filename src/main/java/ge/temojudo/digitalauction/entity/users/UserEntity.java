package ge.temojudo.digitalauction.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "users_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String personalNumber;

    @Transient
    private String jwt;

    @JsonIgnore
    @OneToMany(mappedBy = "registrationUser", fetch = FetchType.LAZY)
    private List<AuctionEntity> registeredAuctions = new ArrayList<>();

    public UserEntity(
            String firstname,
            String lastname,
            String username,
            String password,
            String personalNumber
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.personalNumber = personalNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
