package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import runnermod.cards.tempcards.Bolt;
import runnermod.character.RunnerCharacter;

import java.util.Collections;

public class CardsStance extends RunnerStance {
    public static final String STANCE_ID = "Cards";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Cards");
    private static final String baseDescription = "Each time you play a non Bolt card add a Bolt to your hand NL ";

    private static long sfxId = -1L;
    private int durability;
    public CardsStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);

        this.ID = "Cards";
        this.name = "Blaster";
        this.description = baseDescription;
        updateDescription();
    }



    public void updateAnimation() {
        //Active Particles
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.1F;
                AbstractDungeon.effectsQueue.add(new BackwardsHexFloatParticle());
                AbstractDungeon.effectsQueue.add(new BlinkingHexParticle());
            }
        }

        //Aura Particles
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.4F, 0.5F);
            //RunnerStanceAura bladesAura = new RunnerStanceAura("Wrath");
            //bladesAura.setColour(new Color(0f,255f,85f,0f));
            //AbstractDungeon.effectsQueue.add(bladesAura);
        }
    }
    @Override
    public void onPlayCard(AbstractCard card) {
        if (!(card instanceof Bolt))
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Bolt()));
        super.onPlayCard(card);
        updateDescription();
    }

    public void updateDescription() {
        this.description = baseDescription;
        for (String id: Collections.list(durabilityDictionary.keys())) {
                this.description += id + " : " + durabilityDictionary.get(id) + " durability left NL ";
        }
    }

    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));

    }

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