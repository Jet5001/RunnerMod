package runnermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ArtifactStance;

import java.util.ArrayList;

@SpirePatch2(clz = UseCardAction.class,method = "update")
public class FirewallExhuastPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> FirewallPatchInsert(UseCardAction __instance)
    {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.stance instanceof ArtifactStance)
        {
            __instance.exhaustCard = false;
        }
        return SpireReturn.Continue();
    }
}
