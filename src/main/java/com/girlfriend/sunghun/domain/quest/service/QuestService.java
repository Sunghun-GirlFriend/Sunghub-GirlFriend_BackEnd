package com.girlfriend.sunghun.domain.quest.service;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.auth.domain.repository.UserRepository;
import com.girlfriend.sunghun.domain.auth.facade.UserFacade;
import com.girlfriend.sunghun.domain.quest.domain.QuestTime;
import com.girlfriend.sunghun.domain.quest.domain.Quest;
import com.girlfriend.sunghun.domain.quest.domain.repository.QuestRepository;
import com.girlfriend.sunghun.domain.quest.domain.repository.QuestTimeRepository;
import com.girlfriend.sunghun.domain.quest.domain.type.Status;
import com.girlfriend.sunghun.domain.quest.exception.QuestAlreadyExistsException;
import com.girlfriend.sunghun.domain.quest.exception.QuestNotFoundException;
import com.girlfriend.sunghun.domain.quest.exception.QuestTimeNotFoundException;
import com.girlfriend.sunghun.domain.quest.exception.QuestTimeNotPassedException;
import com.girlfriend.sunghun.domain.quest.presentation.dto.request.ChangeStatusRequest;
import com.girlfriend.sunghun.domain.quest.presentation.dto.request.CreateQuestRequest;
import com.girlfriend.sunghun.domain.quest.presentation.dto.response.QuestListResponse;
import com.girlfriend.sunghun.domain.quest.presentation.dto.response.QuestWaitResponse;
import com.girlfriend.sunghun.global.utils.FilterUtil;
import com.girlfriend.sunghun.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final QuestTimeRepository questTimeRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void createQuest(CreateQuestRequest request) {
        User user = userFacade.queryUser(true);

        questRepository.findByQuestTitle(request.getQuestTitle())
                .ifPresent(m -> {
                    throw QuestAlreadyExistsException.EXCEPTION;
                });

        Quest quest = Quest.builder()
                .questTitle(request.getQuestTitle()).category(request.getCategory())
                .type(request.getType())
                .build();
        quest.setUser(user);
        user.addQuest(quest);
    }

    @Transactional
    public QuestListResponse getListByUser() {
        User user = userFacade.queryUser(true);

        List<Quest> list = questRepository.findAllByUser(user);

        List<QuestWaitResponse> waitList = FilterUtil.statusFilter(list, Status.WAIT).stream()
                .map(it -> {
                    return QuestWaitResponse.builder()
                            .questId(it.getQuestId()).questTitle(it.getQuestTitle())
                            .status(it.getStatus()).category(it.getCategory()).type(it.getType())
                            .userData(it.getUserData())
                            .time(getRestTime(it.getQuestId()))
                            .build();
                }).collect(Collectors.toList());

        return QuestListResponse.builder()
                .runningList(FilterUtil.statusFilter(list, Status.RUNNING))
                .beforeList(FilterUtil.statusFilter(list, Status.BEFORE))
                .completeList(FilterUtil.statusFilter(list, Status.COMPLETE))
                .terminateList(FilterUtil.statusFilter(list, Status.TERMINATE))
                .waitList(waitList)
                .build();
    }

    public String getRestTime(Long id) {
        QuestTime questTime = questTimeRepository.findByQuestId(id)
                .orElseThrow(() -> QuestTimeNotFoundException.EXCEPTION);

        return questTime.getRestHour() + "시간 " + questTime.getRestMinute() + "분 " + questTime.getRestSecond() + "초";
    }

    public void getWaitTime(Long id) {
        QuestTime questTime = questTimeRepository.findByQuestId(id)
                .orElseThrow(() -> QuestTimeNotFoundException.EXCEPTION);

        int hour = questTime.getEndTime().getHour();
        int minute = questTime.getEndTime().getMinute();
        int second = questTime.getEndTime().getSecond();

        int currentHour = LocalDateTime.now().getHour();
        int currentMinute = LocalDateTime.now().getMinute();
        int currentSecond = LocalDateTime.now().getSecond();

        int restHour = hour - currentHour;
        int restMinute = minute - currentMinute;
        if(restMinute < 0) {
            restHour -= 1;
            restMinute += 60;
        }
        int restSecond = second - currentSecond;
        if(restSecond < 0) {
            restMinute -= 1;
            restSecond += 60;
            if(restMinute < 0) {
                restHour -= 1;
                restMinute += 60;
            }
        }

        questTime.setRestTime(restHour, restMinute, restSecond);
        questTimeRepository.save(questTime);
    }

    @Transactional
    public void changeStatus(Long id, ChangeStatusRequest request) {
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> QuestNotFoundException.EXCEPTION);

        quest.changeStatus(request.getStatus());
        if(request.getStatus().equals(Status.RUNNING)) {
            startQuest(quest.getQuestId(), request.getHour());
        } else if(request.getStatus().equals(Status.RESTART)) {
            restartQuest(quest.getQuestId());
            quest.changeStatus(Status.RUNNING);
        } else if(request.getStatus().equals(Status.WAIT)) {
            getWaitTime(quest.getQuestId());
        } else if(request.getStatus().equals(Status.TERMINATE)) {
            QuestTime questTime = questTimeRepository.findByQuestId(quest.getQuestId())
                    .orElseThrow(() -> QuestTimeNotFoundException.EXCEPTION);

            questTimeRepository.delete(questTime);
            quest.changeStatus(Status.TERMINATE);
        }
        questRepository.save(quest);
    }

    public void startQuest(Long id, Long hour) {
        QuestTime time = QuestTime.builder()
                .questId(id).startTime(now())
                .endTime(now().plusHours(hour))
                .build();

        questTimeRepository.save(time);
    }

    public void restartQuest(Long id) {
        QuestTime questTime = questTimeRepository.findByQuestId(id)
                .orElseThrow(() -> QuestTimeNotFoundException.EXCEPTION);

        LocalDateTime endTime = LocalDateTime.now().plusHours(questTime.getRestHour()).plusMinutes(questTime.getRestMinute()).plusSeconds(questTime.getRestSecond());
        questTime.restartTime(LocalDateTime.now(), endTime);

        questTimeRepository.save(questTime);
    }

    @Transactional
    public void checkComplete(Long id) {
        User user = userFacade.queryUser(true);

        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> QuestNotFoundException.EXCEPTION);

        QuestTime questTime = questTimeRepository.findByQuestId(quest.getQuestId())
                .orElseThrow(() -> QuestTimeNotFoundException.EXCEPTION);

        if(questTime.getEndTime().isBefore(now())) {
            quest.changeStatus(Status.COMPLETE);
            questTimeRepository.delete(questTime);
            questRepository.save(quest);

            user.addPoint(50);
            userRepository.save(user);
        } else {
            throw QuestTimeNotPassedException.EXCEPTION;
        }
    }
}
