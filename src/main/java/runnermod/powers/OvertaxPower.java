package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class OvertaxPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("OvertaxPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public OvertaxPower(AbstractCreature owner, int investmentAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, investmentAmount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void onEnergyRecharge() {
        flash();
        AbstractDungeon.player.gainEnergy( this.amount);
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new OvertaxPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
