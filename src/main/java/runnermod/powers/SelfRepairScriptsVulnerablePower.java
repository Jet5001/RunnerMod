package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static runnermod.RunnerMod.makeID;

public class SelfRepairScriptsVulnerablePower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("SelfRepairScriptsVulnerablePower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public SelfRepairScriptsVulnerablePower(AbstractCreature owner, int RepairAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, RepairAmount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        AbstractCreature player = AbstractDungeon.player;
        for (AbstractPower p :player.powers) {
            if (p.ID == VulnerablePower.POWER_ID)
            {
                p.amount = p.amount-amount;
            }
        }
        //This didn't work as it removed all stacks not just one
        //addToBot(new RemoveSpecificPowerAction(player,player, VulnerablePower.POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SelfRepairScriptsVulnerablePower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
