package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class ScrapArmourPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("ScrapArmourPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public ScrapArmourPower(AbstractCreature owner, int scrapAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, scrapAmount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    public void onDurabilityGain(int durability)
    {
        addToBot(new GainBlockAction(AbstractDungeon.player, durability * this.amount));
    }


    @Override
    public AbstractPower makeCopy() {
        return new ScrapArmourPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
