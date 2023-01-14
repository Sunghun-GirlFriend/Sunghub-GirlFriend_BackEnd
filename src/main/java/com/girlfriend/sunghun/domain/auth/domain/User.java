package com.girlfriend.sunghun.domain.auth.domain;

import com.girlfriend.sunghun.domain.quest.domain.Quest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String userPw;

    // profile
    private String name;
    private String profileImage;
    private String description;

    @OneToMany(mappedBy = "", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quest> questList;
    public void addQuest(Quest quest) {
        quest.setUser(this);
        getQuestList().add(quest);
    }

    // point
    private int point;
    public void addPoint(int point) {
        this.point += point;
    }

    @Builder
    public User(String userId, String userPw, String name, String profileImage, String description) {
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.profileImage = profileImage;
        this.description = description;
        this.point = 0;
        this.questList = new ArrayList<>();
    }
}
