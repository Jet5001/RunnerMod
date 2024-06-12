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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import org.lwjgl.Sys;
import runnermod.cards.RandomDebuffAction;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Hacked;

import java.util.Collections;
import java.util.Random;

public class BerserkerStance extends RunnerStance {
    public static final String STANCE_ID = "Berserker";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Berserker");
    private static final String baseDescription = "While in this stance for each card you play deal damage equal to the number of cards played in this stance NL ";

    private static long sfxId = -1L;
    private int durability;
    private int cardsPlayedInStance = 0;

    public BerserkerStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Berserker";
        this.name = "Berserker";
        this.description = baseDescription;
        updateDescription();
    }



    @Override
    public void onPlayCard(AbstractCard card) {
        AbstractCreature p = AbstractDungeon.player;

        super.onPlayCard(card);
            super.onPlayCard(card);
            cardsPlayedInStance ++;
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, cardsPlayedInStance, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE ));
            if (!card.hasTag(RunnerCharacter.Enums.NEON))
            {
                reduceDurability(1);
            }
            if (durabilityDictionary.get("Overclock").equals(0) || durabilityDictionary.get("Overclock") < 0)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeRunnerStanceAction("Neutral", 0));
            }


        if (!card.hasTag(RunnerCharacter.Enums.NEON))
        {
            reduceDurability(1);
        }

        //sort out new stance as durabilties fade
        if (durabilityDictionary.get("Blades").equals(0) || durabilityDictionary.get("Blades") < 0)
        {
            if (durabilityDictionary.get("Overclock").equals(0))
            {
                AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new OverclockStance(new String[]{"Overclock"}, new int[]{durabilityDictionary.get("Overclock")+1} )));

        }
        if (durabilityDictionary.get("Overclock").equals(0) || durabilityDictionary.get("Overclock") < 0)
        {
            if (durabilityDictionary.get("Blades").equals(0))
            {
                AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new BladesStance(new String[]{"Blades"}, new int[]{durabilityDictionary.get("Blades")+1} )));

        }

    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
    }

    public void updateDescription() {
        this.description = baseDescription;
        for (String id: Collections.list(durabilityDictionary.keys())) {
                this.description += " " + id + " : " + durabilityDictionary.get(id) + " turns left NL ";
        }
    }

    public void onEnterStance() {
        cardsPlayedInStance = 0;
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLUE, true));
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