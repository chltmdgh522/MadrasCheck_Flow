package flow.domain.user.domain.entity;



import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String email;



    // 이름
    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String profile;


    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public User(String id, String email, String name,
                String profile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profile = profile;
    }
    public void updateNameAndEmailAndProfile(String name, String email, String profile) {
        this.name = name;
        this.email = email;
        this.profile = profile;
    }
}
