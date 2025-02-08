package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import runnermod.RunnerMod;
import runnermod.character.RunnerCharacter;
import runnermod.util.LocalizedRunnerStanceStrings;

import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedHashMap;

public class AccelStance extends RunnerStance {
    public static String STANCE_ID = "Accel";

    private static long sfxId = -1L;
    private int durability;
    public int strengthGained = 0;

    public AccelStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Accel";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        name = stanceString.NAME;
        this.description = baseDescription;
        updateDescription();
        strengthGained = 0;
    }
    public AccelStance(LinkedHashMap<String,Integer> newDurabilities) {
        super(newDurabilities);
        this.ID = "Accel";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        name = stanceString.NAME;
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

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player, 2)));
        strengthGained+= 2;
        super.onPlayCard(card);
        updateDescription();
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
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
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(p,-strengthGained)));
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}