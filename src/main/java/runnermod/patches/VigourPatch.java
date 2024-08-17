package runnermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.xml.sax.Locator;
import runnermod.character.RunnerCharacter;

import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SpirePatch2(clz = VigorPower.class,method = "onUseCard")
public class VigourPatch {
    //private static final Logger logger = LogManager.getLogManager().getLogger();

    @SpireInsertPatch(locator = VigorLocator.class)
    public static SpireReturn<Void> VigorPathInsert(VigorPower __instance, AbstractCard card)
    {
        if (card.hasTag(RunnerCharacter.Enums.CONSERVE_VIGOUR))
        {
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();

    }



    private static class VigorLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(VigorPower.class,"flash");
            return LineFinder.findInOrder(ctMethodToPatch,new ArrayList(),(Matcher)finalMatcher);
        }
    }

}
