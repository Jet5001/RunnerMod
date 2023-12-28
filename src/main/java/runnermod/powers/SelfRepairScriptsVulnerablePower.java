package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
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
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        AbstractCreature player = AbstractDungeon.player;
        addToBot(new ReducePowerByXAction(VulnerablePower.POWER_ID, player,amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SelfRepairScriptsVulnerablePower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
