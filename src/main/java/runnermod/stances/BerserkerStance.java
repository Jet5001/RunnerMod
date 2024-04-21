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
    private static final String baseDescription = "While in this stance for each card you play perform a random action";

    private static long sfxId = -1L;
    private int durability;
    private boolean skipNextTrigger = false;

    public BerserkerStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Berserker";
        this.name = "Berserker";
        this.description = baseDescription;
        updateDescription();
    }

    private void performRandomAction(AbstractCard cardPlayed)
    {
        AbstractPlayer p = AbstractDungeon.player;
        int randEffectNum = new Random().nextInt(12);
        //Discard A card
        if (randEffectNum < 1)
        {
            System.out.println("Discard Card");
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(p,p,1,true));
            return;
        }
        //Play a random card
        if (randEffectNum < 2)
        {
            System.out.println("Play Card");
            randEffectNum = new Random().nextInt(3);
            if (randEffectNum <=0)
            {
                //Play Random card from discard
                if (!AbstractDungeon.player.hand.isEmpty()) {
                    AbstractCard card =  AbstractDungeon.player.hand.getRandomCard(new com.megacrit.cardcrawl.random.Random());
                    AbstractDungeon.player.drawPile.group.remove(card);
                    AbstractDungeon.getCurrRoom().souls.remove(card);
                    AbstractDungeon.player.limbo.group.add(card);
                    card.current_y = -200.0F * Settings.scale;
                    card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    card.target_y = (float)Settings.HEIGHT / 2.0F;
                    card.targetAngle = 0.0F;
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(card, AbstractDungeon.getRandomMonster(), false, true));
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    if (!Settings.FAST_MODE) {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                    skipNextTrigger = true;
                    return;
                }


            }
            if (randEffectNum <=1)
            {
                //Play Random card from discard
                if (!AbstractDungeon.player.discardPile.isEmpty()) {
                    AbstractCard card =  AbstractDungeon.player.discardPile.getRandomCard(new com.megacrit.cardcrawl.random.Random());
                    AbstractDungeon.player.drawPile.group.remove(card);
                    AbstractDungeon.getCurrRoom().souls.remove(card);
                    AbstractDungeon.player.limbo.group.add(card);
                    card.current_y = -200.0F * Settings.scale;
                    card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    card.target_y = (float)Settings.HEIGHT / 2.0F;
                    card.targetAngle = 0.0F;
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(card, AbstractDungeon.getRandomMonster(), false, true));
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    if (!Settings.FAST_MODE) {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                    skipNextTrigger = true;
                    return;
                }

            }
            //Play top card of deck (default if no cards in other places)
            AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(p,false));
            skipNextTrigger = true;
            return;

        }
        //Gain energy equal to the cost of the card
        if (randEffectNum < 3)
        {
            System.out.println("Refund Cost");
            if (cardPlayed.cost >0)
            {
                p.gainEnergy(cardPlayed.cost);
            }
            return;
        }
        //Draw a card
        if (randEffectNum < 4)
        {
            System.out.println("Draw Card");
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
            return;
        }
        //Gain Block
        if (randEffectNum < 5)
        {
            System.out.println("Gain Block");
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,5));
            return;
        }
        //Strike
        if (randEffectNum < 6)
        {
            System.out.println("Deal Damage");
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p,5, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            return;
        }
        //Apply a random debuff
        if (randEffectNum < 7)
        {
            System.out.println("Apply Random Debuff");
            AbstractCreature mon = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new RandomDebuffAction(p,mon));
            return;
        }
        //Apply 3 hack
        if (randEffectNum < 8)
        {
            System.out.println("Apply Hack");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerToRandomEnemyAction(p, new Hacked(p, 3)));
            return;
        }
        //Gain a card back from discard
        if (randEffectNum < 9)
        {
            System.out.println("Gain Card From Discard");
            if(!p.discardPile.isEmpty() && p.hand.size()<10)
            {
                AbstractCard c = p.discardPile.getRandomCard(new com.megacrit.cardcrawl.random.Random());
                p.hand.addToHand(c);
                p.discardPile.removeCard(c);
                return;
            }

        }
        //Gain Gold
        if (randEffectNum < 10)
        {
            System.out.println("Gain Gold");
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(15));
            return;
        }
        //Gain Str
        if (randEffectNum < 11)
        {
            System.out.println("Gain Str");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,new StrengthPower(p, 2)));
            return;
        }
        //Gain Dex
        if (randEffectNum < 12)
        {
            System.out.println("Gain Dex");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,new DexterityPower(p, 2)));
            return;
        }
    }


    @Override
    public void onPlayCard(AbstractCard card) {
        AbstractCreature p = AbstractDungeon.player;

        super.onPlayCard(card);
        if(!skipNextTrigger)
        {
            performRandomAction(card);
        }
        else
        {
            skipNextTrigger = false;
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