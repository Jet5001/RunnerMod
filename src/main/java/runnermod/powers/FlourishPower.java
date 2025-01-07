package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class FlourishPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("FlourishPower");
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;
    public FlourishPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToTop(new RemoveSpecificPowerAction(this.owner,this.owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new FlourishPower(owner, amount);
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 3 + DESCRIPTIONS[1];
    }
}
