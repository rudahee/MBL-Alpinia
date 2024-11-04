package net.rudahee.movies.user.model.db;

import jakarta.persistence.*;
import lombok.*;
import net.rudahee.movies.group.model.db.GroupEntity;
import net.rudahee.movies.user_movie.model.db.UserMovieEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHashed;

    private String email;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Instant deleteAt;

    private Instant lastPasswordChange;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private boolean enabled;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<GroupEntity> groups;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "user")
    private Set<UserMovieEntity> movies;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).toList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        boolean expired = false;
        if (deleteAt == null) {
            expired = true;
        } else {
            expired = this.deleteAt.isAfter(Instant.now());
        }
        return expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        boolean expired = false;
        if (deleteAt == null) {
            expired = true;
        } else {
            expired = this.lastPasswordChange.isBefore(lastPasswordChange.plus(6, ChronoUnit.MONTHS));
        }
        return expired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getPassword() {
        return this.passwordHashed;
    }

}
