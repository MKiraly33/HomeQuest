package com.mate.kiraly.HomeQuest.application.service;

import com.mate.kiraly.HomeQuest.application.dto.QuestCreateRequest;
import com.mate.kiraly.HomeQuest.application.modell.Quest;
import com.mate.kiraly.HomeQuest.application.repository.QuestRepository;
import com.mate.kiraly.HomeQuest.userhandling.appuser.AppUser;
import com.mate.kiraly.HomeQuest.userhandling.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestServiceImpl implements QuestService{

    private final AppUserService userService;
    private final QuestRepository questRepository;
    @Transactional
    @Override
    public void createQuest(QuestCreateRequest request) {
        Quest quest = new Quest(
                request.getQuestName(),
                request.getQuestDescription(),
                request.getQuestReward(),
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                (AppUser) userService.loadUserByUsername(request.getQuestReceiverEmail()));
        quest.setQuestGivenAt(LocalDateTime.now());
        questRepository.save(quest);
    }

    @Override
    public List<Quest> getQuestsForCurrentUser() {
        List<Quest> quests = questRepository.findByQuestReceiver(
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        quests.sort(new Comparator<Quest>() {
            @Override
            public int compare(Quest o1, Quest o2) {
                if(o1.getQuestGivenAt().isBefore(o2.getQuestGivenAt())){
                    return 1;
                } else if (o2.getQuestGivenAt().isBefore(o1.getQuestGivenAt())) {
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        return quests;
    }

    @Override
    public List<Quest> getQuestsByCurrentUser() {
        List<Quest> quests = questRepository.findByQuestGiver(
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        quests.sort(new Comparator<Quest>() {
            @Override
            public int compare(Quest o1, Quest o2) {
                if((o1.getIsCompleted() && o2.getIsCompleted()) || (!o1.getIsCompleted() && !o2.getIsCompleted())) {
                    if (o1.getQuestGivenAt().isBefore(o2.getQuestGivenAt())) {
                        return 1;
                    } else if (o2.getQuestGivenAt().isBefore(o1.getQuestGivenAt())) {
                        return -1;
                    } else {
                        return 0;
                    }
                }else{
                    if(o1.getIsCompleted() && !o2.getIsCompleted()){
                        return 1;
                    }else if(!o1.getIsCompleted() && o2.getIsCompleted()){
                        return -1;
                    }
                    return 0;
                }
            }
        });
        return quests;
    }

    @Override
    public String getHoursBetweenNowAndQuestGiven(LocalDateTime questGivenAt){
        Long hoursBetween = ChronoUnit.HOURS.between(questGivenAt, LocalDateTime.now());
        return hoursBetween.toString();
    }
    @Transactional
    @Override
    public void questOperation(Optional<String> operation, Optional<String> questId) {
        if(operation.isEmpty() || questId.isEmpty()){
            return; //TODO: failure
        }
        Long id = Long.parseLong(questId.get());
        switch (operation.get()){
            case "cancel":
                Optional<Quest> optionalQuest = questRepository.findById(id);
                Quest q = null;
                if(optionalQuest.isPresent()){
                    q = optionalQuest.get();
                    if(!q.getIsCompleted() && ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() == q.getQuestGiver().getId()) {
                        q.setIsCancelled(true);
                        q.setIsCompleted(true);
                        questRepository.save(q);
                    }else{
                        return;
                    }
                }
                break;
            case "complete":
                Optional<Quest> optionalQuest2 = questRepository.findById(id);
                Quest q2 = null;
                if(optionalQuest2.isPresent()){
                    q2 = optionalQuest2.get();
                    if(!q2.getIsCompleted() && ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() == q2.getQuestGiver().getId()) {
                        q2 = optionalQuest2.get();
                        q2.setIsCompleted(true);
                        questRepository.save(q2);
                    }
                }
                break;
            default: return; //TODO: error
        }
    }
}
