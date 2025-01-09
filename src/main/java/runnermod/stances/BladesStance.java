package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import runnermod.RunnerMod;
import runnermod.character.RunnerCharacter;
import runnermod.util.LocalizedRunnerStanceStrings;

import java.util.Collections;

public class BladesStance extends RunnerStance {
    public static String STANCE_ID = "Blades";


    private static long sfxId = -1L;
    private int durability;

    public BladesStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Blades";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        this.name = stanceString.NAME;
        this.description = baseDescription;
        updateDescription();
    }

//    public float atDamageGive(float damage, DamageInfo.DamageType type) {
//        if (type == DamageInfo.DamageType.NORMAL)
//            return damage * 2.0F;
//        return damage;
//    }
//
//    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
//        if (type == DamageInfo.DamageType.NORMAL)
//            return damage * 2.0F;
//        return damage;
//    }


    @Override
    public void onPlayCard(AbstractCard card) {
        //check for x cost at -1
        if (card.cost < -1)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, card.energyOnUse*3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        else if(card.cost > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, card.cost*3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        super.onPlayCard(card);
        updateDescription();
    }

    public void updateAnimation() {
        //Active Particles
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.1F;
                AbstractDungeon.effectsQueue.add(new BackwardsHexFloatParticle());
                //AbstractDungeon.effectsQueue.add(new BlinkingHexParticle());
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


    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        //Particles that trigger once on stance entry
        AbstractDungeon.effectsQueue.add(new RunnerStanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Blades"));
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