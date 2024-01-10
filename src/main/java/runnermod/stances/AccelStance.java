package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;

import java.util.Collections;

public class AccelStance extends RunnerStance {
    public static final String STANCE_ID = "Accel";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Accel");

    private static long sfxId = -1L;
    private int durability;

    public AccelStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Accel";
        this.name = "Accel";
        this.description = "Each time you play an attack gain block equal to it's cost";
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
        System.out.println("USED CARD IN ACCEL STANCE");

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player, 1)));
        reduceDurability();
        if (durabilityDictionary.get("Brute") == 0)
        {
            if (durabilityDictionary.get("Agility") == 0)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
            }
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new AgilityStance(new String[]{"Agility"}, new int[]{durabilityDictionary.get("Agility")} )));

        }
        if (durabilityDictionary.get("Agility") == 0)
        {
            if (durabilityDictionary.get("Brute") == 0)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
            }
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new AgilityStance(new String[]{"Brute"}, new int[]{durabilityDictionary.get("Brute")} )));

        }
        super.onPlayCard(card);
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

    public void updateDescription() {
        this.description = "";
        for (String id: Collections.list(durabilityDictionary.keys())) {
                this.description += id + " : " + durabilityDictionary.get(id) + " turns left";
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