package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class WallOfStaticPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("WallOfStaticPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public WallOfStaticPower(AbstractCreature owner, int investmentAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, investmentAmount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (!(target instanceof AbstractPlayer) && (power.type == PowerType.DEBUFF))
        {
            addToBot(new GainBlockAction(source,this.amount));
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new WallOfStaticPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
