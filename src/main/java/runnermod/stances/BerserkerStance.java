package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;
import org.lwjgl.Sys;
import runnermod.RunnerMod;
import runnermod.cards.RandomDebuffAction;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Hacked;
import runnermod.util.LocalizedRunnerStanceStrings;

import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.Random;

public class BerserkerStance extends RunnerStance {
    public static String STANCE_ID = "Berserker";


    private static long sfxId = -1L;
    private int durability;
    private int cardsPlayedInStance = 0;

    public BerserkerStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Berserker";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        name = stanceString.NAME;
        this.description = baseDescription;
        updateDescription();
    }

    public BerserkerStance(LinkedHashMap<String,Integer> newDurabilities) {
        super(newDurabilities);
        this.ID = "Berserker";
        stanceString = LocalizedRunnerStanceStrings.getRunnerStanceStrings(RunnerMod.makeID(STANCE_ID));
        baseDescription = stanceString.DESCRIPTION;
        name = stanceString.NAME;
        this.description = baseDescription;
        updateDescription();
    }


    @Override
    public void updateDescription() {
        super.updateDescription();
        description += stanceString.EXTRA_DESCRIPTIONS[0] + (cardsPlayedInStance+1) + stanceString.EXTRA_DESCRIPTIONS[1];
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        super.onPlayCard(card);
        cardsPlayedInStance ++;
        for (AbstractMonster m:AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, cardsPlayedInStance, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE,true ));
        }

        //sort out new stance as durabilties fade

        updateDescription();
    }

    public void updateAnimation() {
        //Active Particles
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.1F;
                AbstractDungeon.effectsQueue.add(new BackwardsHexFloatParticle());
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
        cardsPlayedInStance = 0;
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLUE, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "WRATH"));
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