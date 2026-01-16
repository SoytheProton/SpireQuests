package spireQuests.quests.soytheproton.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import spireQuests.abstracts.AbstractSQRelic;
import spireQuests.quests.soytheproton.patches.OnLoseHPInfoPatch;

import static spireQuests.Anniv8Mod.makeID;

public class IoMoth extends AbstractSQRelic implements OnLoseHPInfoPatch.OnLoseHPInfoInterface {
    public static final String ID = makeID(IoMoth.class.getSimpleName());
    public IoMoth() {
        super(ID, "soytheproton", RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public static boolean hasTakenDamageThisCombat;

    public void onEquip() {
        ++AbstractDungeon.player.masterHandSize;
    }

    public void onUnequip() {
        --AbstractDungeon.player.masterHandSize;
    }

    public void atPreBattle() {
        hasTakenDamageThisCombat = false;
    }

    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
    }


    public void atTurnStart() {
        if(!hasTakenDamageThisCombat) flash();
    }

    @Override
    public void onLoseHPInfo(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount >= 1 && !hasTakenDamageThisCombat) {
            flash();
            AbstractDungeon.player.gameHandSize--;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            hasTakenDamageThisCombat = true;
            grayscale = true;
        }
    }
}
