package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class DepletedPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("DepletedPower");
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;
    public DepletedPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }

    @Override
    public void onEnergyRecharge() {
        this.flash();
        AbstractDungeon.player.loseEnergy(this.amount);
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }


    @Override
    public AbstractPower makeCopy() {
        return new DepletedPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
