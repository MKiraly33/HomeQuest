package com.mate.kiraly.HomeQuest.application.controller;

import com.mate.kiraly.HomeQuest.application.dto.QuestCreateRequest;
import com.mate.kiraly.HomeQuest.application.service.QuestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class QuestController {

    private final QuestService questService;

    @GetMapping(path = "/")
    public String indexPage(Model model){
        return "home";
    }

    @GetMapping(path = "home")
    public String homePage(Model model){
        return "home";
    }

    @GetMapping(path = "createquest")
    public String createQuestPage(Model model){
        return "createquest";
    }

    @PostMapping(path = "createquest")
    public String createQuest(Model model, @RequestParam Map<String, String> request){
        QuestCreateRequest questCreateRequest = new QuestCreateRequest(
                request.get("questName"),
                request.get("questDescription"),
                request.get("questReward"),
                request.get("questReceiverEmail")
        );
        questService.createQuest(questCreateRequest);
        model.addAttribute("createSuccess", true);
        model.addAttribute("createdName", questCreateRequest.getQuestName());
        return "createquest";
    }

    @GetMapping(path = "listquests")
    public String listQuests(Model model){
        model.addAttribute("quests", questService.getQuestsForCurrentUser());
        model.addAttribute("helper", questService);
        return "listquests";
    }

    @GetMapping(path = "managequests")
    public String manageQuests(Model model, @RequestParam Optional<String> operation, @RequestParam Optional<String> id){
        questService.questOperation(operation, id);
        model.addAttribute("quests", questService.getQuestsByCurrentUser());
        model.addAttribute("helper", questService);
        return "manageQuests";
    }
}
