package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import runnermod.RunnerMod;
import runnermod.util.LocalizedRunnerStanceStrings;
import runnermod.util.RunnerStanceStrings;

import java.util.Collections;

public class WallStance extends RunnerStance {
    public static String STANCE_ID = "Wall";

    private static long sfxId = -1L;
    private int durability;

    public WallStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Wall";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        name = stanceString.NAME;
        this.description = baseDescription;
        updateDescription();
    }



    @Override
    public void onPlayCard(AbstractCard card) {

        //check for x cost when -1
        if(card.cost == -1)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, card.energyOnUse*3));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, card.cost*3));
        }

        super.onPlayCard(card);
        updateDescription();
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathHexParticle());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            //AbstractDungeon.effectsQueue.add(new StanceAuraEffect("CALM"));
        }
    }

    //start visuals for this stance
    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.DARK_GRAY, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "CALM"));
    }

    //stop visuals for the stance
    public void onExitStance() {
        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }
}