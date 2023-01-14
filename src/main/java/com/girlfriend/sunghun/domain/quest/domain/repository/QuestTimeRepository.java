package com.girlfriend.sunghun.domain.quest.domain.repository;

import com.girlfriend.sunghun.domain.quest.domain.QuestTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestTimeRepository extends JpaRepository<QuestTime, Long> {

    Optional<QuestTime> findByQuestId(Long questId);

}
