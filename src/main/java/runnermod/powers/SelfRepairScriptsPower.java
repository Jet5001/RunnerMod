package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.uncommon.RepairAction;

import static runnermod.RunnerMod.makeID;

public class SelfRepairScriptsPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("SelfRepairScripts");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public SelfRepairScriptsPower(AbstractCreature owner, int RepairAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, RepairAmount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new RepairAction(1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SelfRepairScriptsPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount;
    }
}
