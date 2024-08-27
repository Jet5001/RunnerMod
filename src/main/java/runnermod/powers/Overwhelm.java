package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.PostPowerApplySubscriber;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class Overwhelm extends BasePower implements CloneablePowerInterface, HealthBarRenderPower {


    public static final String POWER_ID = makeID("Overwhelm");
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;
    public Overwhelm(AbstractCreature owner, int overwhelmAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, overwhelmAmount);
    }



    @Override
    public AbstractPower makeCopy() {
        return new Overwhelm(owner, amount);
    }


    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            //For some reason crashes if not thorns type damage?
            addToBot(new DamageAction(owner, new DamageInfo(source, this.amount * AbstractDungeon.actionManager.cardsPlayedThisTurn.size(), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new RemoveSpecificPowerAction(this.owner,source,this));
        }
    }


    public void updateDescription() {
        if (AbstractDungeon.player instanceof RunnerCharacter) {
            this.description = DESCRIPTIONS[0] + amount * AbstractDungeon.actionManager.cardsPlayedThisTurn.size() + DESCRIPTIONS[1];
        }
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount * AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
    }

    @Override
    public Color getColor() {
        return Color.PURPLE;
    }
}
