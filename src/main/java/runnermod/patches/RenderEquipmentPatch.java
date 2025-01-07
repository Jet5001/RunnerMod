package runnermod.patches;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import runnermod.EquipmentRenderer;

public class RenderEquipmentPatch {

    @SpirePatch2(clz = AbstractPlayer.class, method = "render")
    public static class RenderEquipment {
        
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(SpriteBatch sb) {
            EquipmentRenderer.renderEquipment(sb);
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "renderHealth");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }

    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "update")
    public static class updateEquipment {

        @SpirePrefixPatch
        public static void Prefix() {
            if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.EVENT) {
                EquipmentRenderer.updateEquipment();
            }
        }
    }
}
