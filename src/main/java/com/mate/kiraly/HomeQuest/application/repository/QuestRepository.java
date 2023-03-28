package com.mate.kiraly.HomeQuest.application.repository;

import com.mate.kiraly.HomeQuest.application.modell.Quest;
import com.mate.kiraly.HomeQuest.userhandling.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByQuestGiver(AppUser questGiver);
    List<Quest> findByQuestReceiver(AppUser questReceiver);
}
