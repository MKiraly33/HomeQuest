package com.mate.kiraly.HomeQuest.application.service;

import com.mate.kiraly.HomeQuest.application.dto.QuestCreateRequest;
import com.mate.kiraly.HomeQuest.application.modell.Quest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestService {

    public void createQuest(QuestCreateRequest request);
    public List<Quest> getQuestsForCurrentUser();
    public List<Quest> getQuestsByCurrentUser();
    public String getHoursBetweenNowAndQuestGiven(LocalDateTime questGivenAt);
    public String questOperation(Optional<String> operation, Optional<String> questId, Optional<String> requestor);
}
