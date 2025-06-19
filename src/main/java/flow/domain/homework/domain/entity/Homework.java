package flow.domain.homework.domain.entity;

import flow.domain.user.domain.entity.User;
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
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // user_id가 Homework 테이블에 외래키로 생성됨
    private User user;


    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin")
    private String extension; // 대소문자 구분을 위해


    @Enumerated(EnumType.STRING)
    private ExtensionType type; // FIXED or CUSTOM

    private boolean selected; // CUSTOM은 무조건 False임

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Homework(User user, String extension, ExtensionType type, boolean selected) {
        this.user = user;
        this.extension = extension;
        this.type = type;
        this.selected = selected;
    }

    public void updateSelect(boolean selected) {
        this.selected = selected;
    }
}
