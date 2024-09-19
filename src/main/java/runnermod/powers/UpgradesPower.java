package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.common.UpgradeRandomCardsInHandAction;

import static runnermod.RunnerMod.makeID;

public class UpgradesPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("UpgradesPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public UpgradesPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new UpgradeRandomCardsInHandAction(amount));
        super.atStartOfTurnPostDraw();
    }

    @Override
    public AbstractPower makeCopy() {
        return new UpgradesPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
