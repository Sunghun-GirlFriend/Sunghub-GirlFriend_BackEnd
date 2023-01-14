package com.girlfriend.sunghun.domain.quest.domain;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.quest.domain.type.Category;
import com.girlfriend.sunghun.domain.quest.domain.type.QuestType;
import com.girlfriend.sunghun.domain.quest.domain.type.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public void setUser(User user) {
        this.user = user;
    }

    // quest
    private String questTitle;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    public void changeStatus(Status status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    private QuestType type;

    @Builder
    public Quest(String questTitle, Category category, QuestType type) {
        this.questTitle = questTitle;
        this.category = category;
        this.status = Status.BEFORE;
        this.type = type;
    }
}
