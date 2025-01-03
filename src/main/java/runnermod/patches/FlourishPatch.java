package runnermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import runnermod.character.RunnerCharacter;
import runnermod.powers.FlourishPower;

import java.util.ArrayList;

@SpirePatch2(clz = AbstractCreature.class,method = "addBlock")
public class FlourishPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> FlourishPatchInsert(AbstractCreature __instance)
    {
        if(__instance.isPlayer)
        {
            for (AbstractCreature c: AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower p:c.powers ) {
                    if(p instanceof FlourishPower)
                    {
                        p.onSpecificTrigger();
                    }
                }
            }
        }
        return SpireReturn.Continue();
    }

}
