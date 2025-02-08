package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import runnermod.RunnerMod;
import runnermod.character.RunnerCharacter;
import runnermod.util.LocalizedRunnerStanceStrings;

import java.util.Collections;

public class OverclockStance extends RunnerStance {
    public static String STANCE_ID = "Overclock";


    private static long sfxId = -1L;
    private int durability;
    int cardsPlayedInStance;
    public OverclockStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Overclock";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        name = stanceString.NAME;
        this.description = baseDescription;
        updateDescription();
    }



    @Override
    public void onPlayCard(AbstractCard card) {
        super.onPlayCard(card);
        AbstractDungeon.actionManager.addToBottom(new DiscardLeftMostAction(AbstractDungeon.player,AbstractDungeon.player,1));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        updateDescription();
    }



    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new BackwardsHexFloatParticle(Color.YELLOW));
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            //AbstractDungeon.effectsQueue.add(new RunnerStanceAura("Blades"));
        }
    }


    //start visuals for this stance
    public void onEnterStance() {
        cardsPlayedInStance = 0;
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.DARK_GRAY, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "WRATH"));
    }

    //stop visuals for the stance
    public void onExitStance() {
        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}