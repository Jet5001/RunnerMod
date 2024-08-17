package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import org.lwjgl.Sys;
import runnermod.cards.tempcards.*;
import runnermod.character.RunnerCharacter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AKIRAStance extends RunnerStance {
    public static final String STANCE_ID = "AKIRA";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("AKIRA");
    private static final String baseDescription = "You can't enter new stances";

    private static long sfxId = -1L;
    private int durability;
    com.megacrit.cardcrawl.random.Random rng = new com.megacrit.cardcrawl.random.Random();
    public AKIRAStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "AKIRA";
        this.name = "AKIRA";
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
        this.description = baseDescription;
    }

    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));

        int totalCards = AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size()+ AbstractDungeon.player.hand.size();
        System.out.println(totalCards);
        System.out.println(totalCards/5);
        System.out.println(Math.round(totalCards/5));
        int amountToRemove = Math.max(3,Math.round((float)totalCards/5));
        AKIRAExhaust(amountToRemove);
        AKIRAAdd(amountToRemove);

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
    private void AKIRAExhaust(int amountToRemove)
    {

        CardGroup starters = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup others = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c:AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND))
            {
                starters.addToRandomSpot(c);
            }
            else
            {
                others.addToRandomSpot(c);
            }
        }
        for (AbstractCard c:AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND))
            {
                starters.addToRandomSpot(c);
            }
            else
            {
                others.addToRandomSpot(c);
            }
        }
        for (AbstractCard c:AbstractDungeon.player.hand.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND))
            {
                starters.addToRandomSpot(c);
            }
            else
            {
                others.addToRandomSpot(c);
            }
        }
        starters.shuffle();
        others.shuffle();
        amountToRemove = Math.min(amountToRemove, starters.size() + others.size());
        if (starters.size() <= amountToRemove)
        {
            amountToRemove -= starters.size();
            for (AbstractCard c:starters.group) {
                exhaustCard(c, starters);
            }

            for (int i = 0; i < amountToRemove; i++) {
                AbstractCard c = others.getRandomCard(rng);
                exhaustCard(c, others);

            }
        }
        else
        {
            com.megacrit.cardcrawl.random.Random rng = new com.megacrit.cardcrawl.random.Random();
            for (int i = 0; i < amountToRemove; i++) {
                AbstractCard c = starters.getRandomCard(rng);
                exhaustCard(c, starters);
            }
        }

    }

    private void exhaustCard(AbstractCard c , CardGroup ownerGroup)
    {
        if (AbstractDungeon.player.hand.contains(c))
        {
            AbstractDungeon.player.hand.removeCard(c);
        }
        if (AbstractDungeon.player.discardPile.contains(c))
        {
            AbstractDungeon.player.discardPile.removeCard(c);
        }
        if (AbstractDungeon.player.drawPile.contains(c))
        {
            AbstractDungeon.player.drawPile.removeCard(c);
        }
        ownerGroup.moveToExhaustPile(c);
    }

    private void AKIRAAdd(int cardsToAdd)
    {
        CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        possibleCards.addToRandomSpot(new Apex());
        possibleCards.addToRandomSpot(new Drift());
        possibleCards.addToRandomSpot(new FatalError());
        possibleCards.addToRandomSpot(new Godspeed());
        possibleCards.addToRandomSpot(new RegenFactor());
        possibleCards.addToRandomSpot(new SpaceLaser());
        possibleCards.addToRandomSpot(new Autopilot());
        possibleCards.addToRandomSpot(new Biohazard());
        possibleCards.addToRandomSpot(new Polyphony());
        possibleCards.addToRandomSpot(new TheWeaknessOfFlesh());
        possibleCards.addToRandomSpot(new WallOfForce());
        AbstractCard tempDecoy = new Decoy();
        tempDecoy.rarity = AbstractCard.CardRarity.RARE;
        possibleCards.addToRandomSpot(tempDecoy);
        possibleCards.shuffle();
        AbstractCard randomCard;
        for (int i = 0; i < cardsToAdd; i++) {
            randomCard = possibleCards.getRandomCard(rng);
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(randomCard.makeCopy(),1, true, true));
        }
    }


}


