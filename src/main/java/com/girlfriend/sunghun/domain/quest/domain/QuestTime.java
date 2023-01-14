package com.girlfriend.sunghun.domain.quest.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int restHour;
    private int restMinute;
    private int restSecond;

    public void setRestTime(int restHour, int restMinute, int restSecond) {
        this.restHour = restHour;
        this.restMinute = restMinute;
        this.restSecond = restSecond;
    }

    public void restartTime(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Builder
    public QuestTime(Long questId, LocalDateTime startTime, LocalDateTime endTime) {
        this.questId = questId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
