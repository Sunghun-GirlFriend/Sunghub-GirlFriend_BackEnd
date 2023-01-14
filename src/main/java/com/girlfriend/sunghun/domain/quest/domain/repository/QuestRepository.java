package com.girlfriend.sunghun.domain.quest.domain.repository;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

    Optional<Quest> findByQuestTitle(String questTitle);

    List<Quest> findAllByUser(User user);

}
