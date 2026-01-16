package spireQuests.quests.soytheproton.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class OnLoseHPInfoPatch {
    public interface OnLoseHPInfoInterface {
        void onLoseHPInfo(DamageInfo info, int damageAmount);
    }

    @SpirePatch2(clz= AbstractPlayer.class,method = "damage")
    public static class LoseHPHookPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"r","damageAmount"})
        public static void Insert(DamageInfo info, AbstractRelic r, int damageAmount) {
            if(r instanceof OnLoseHPInfoInterface) ((OnLoseHPInfoInterface) r).onLoseHPInfo(info, damageAmount);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class, "onLoseHp");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
