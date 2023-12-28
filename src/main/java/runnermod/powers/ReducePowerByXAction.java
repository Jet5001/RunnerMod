package runnermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ReducePowerByXAction extends AbstractGameAction {

    String powerID;
    AbstractCreature target;
    int reduceAmount;
    ReducePowerByXAction(String powerID, AbstractCreature target, int reduceAmount)
    {
        this.powerID = powerID;
        this.target = target;
        this.reduceAmount = reduceAmount;
    }


    @Override
    public void update() {
        for (AbstractPower p: target.powers) {
            if (p.ID == powerID)
            {
                if (p.amount > reduceAmount)
                {
                    p.amount = p.amount-reduceAmount;
                }
                else
                {
                    addToTop(new RemoveSpecificPowerAction(target,target,powerID));
                }
            }
        }
        this.isDone = true;
    }
}
