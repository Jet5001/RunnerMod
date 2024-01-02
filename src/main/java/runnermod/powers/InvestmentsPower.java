package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.tempcards.Interest;

import static runnermod.RunnerMod.makeID;

public class InvestmentsPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("InvestmentsPower");
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;
    public InvestmentsPower(AbstractCreature owner, int investmentAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, investmentAmount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        //addToBot(new MakeTempCardInHandAction(new Interest(), 1, false));
        addToBot(new GainGoldAction(15));
        addToBot(new ReducePowerAction(owner,owner,InvestmentsPower.POWER_ID,1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new InvestmentsPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
