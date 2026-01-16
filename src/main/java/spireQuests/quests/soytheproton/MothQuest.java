package spireQuests.quests.soytheproton;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spireQuests.patches.QuestTriggers;
import spireQuests.quests.AbstractQuest;
import spireQuests.quests.QuestReward;
import spireQuests.quests.soytheproton.relics.IoMoth;

public class MothQuest extends AbstractQuest {
    public MothQuest() {
        super(QuestType.SHORT, QuestDifficulty.HARD);

        new TriggerTracker<>(QuestTriggers.ACT_CHANGE,1)
                .hide()
                .add(this);
        new TriggeredUpdateTracker<Integer, Integer>(QuestTriggers.UNBLOCKED_ATTACK_DAMAGE_TAKEN,
                0, 25, Integer::sum, ()->false) {

            @Override
            public boolean isComplete() {
                return !isFailed();
            }
            @Override
            public boolean isFailed() {
                return (condition == null || condition.get()) && state >= target;
            }
        }
                .add(this);
        addReward(new QuestReward.RelicReward(new IoMoth()));
    }

    @Override
    public boolean canSpawn() {
        // This will make it so it spawns on the first five floors of Act 1 and 2, assuming no shenanigans.
        return AbstractDungeon.floorNum <= 5 || AbstractDungeon.floorNum > 17 && AbstractDungeon.floorNum <= 23;
    }
}
