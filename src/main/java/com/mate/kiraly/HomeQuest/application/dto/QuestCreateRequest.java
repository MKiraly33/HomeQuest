package com.mate.kiraly.HomeQuest.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestCreateRequest {
    private String questName;
    private String questDescription;
    private String questReward;
    private String questReceiverEmail;
}
