package com.mate.kiraly.HomeQuest.application.modell;

import com.mate.kiraly.HomeQuest.userhandling.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Quest {

    @Id
    @SequenceGenerator(name="quest_sequence",sequenceName = "quest_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quest_sequence")
    private Long id;
    @Column(nullable = false)
    private String questName;
    @Column(nullable = false, length = 3000)
    private String questDescription;
    @Column(nullable = false)
    private String questReward;
    @ManyToOne
    private AppUser questGiver;
    @ManyToOne
    private AppUser questReceiver;
    @Column(nullable = false)
    private Boolean isCompleted;
    @Column(nullable = false)
    private Boolean isCancelled;
    @Column(nullable = false)
    private LocalDateTime questGivenAt;
    private LocalDateTime questCompletedAt;

    public Quest(String questName,
                 String questDescription,
                 String questReward,
                 AppUser questGiver,
                 AppUser questReceiver) {
        this.questName = questName;
        this.questDescription = questDescription;
        this.questReward = questReward;
        this.questGiver = questGiver;
        this.questReceiver = questReceiver;
        this.isCompleted = false;
        this.isCancelled = false;
    }
}
