package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.PostPowerApplySubscriber;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class EvasiveManoeuvresPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("EvasiveManoeuvresPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;
    public EvasiveManoeuvresPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new ReducePowerAction(owner,owner, EvasiveManoeuvresPower.POWER_ID,1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new EvasiveManoeuvresPower(owner, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        addToTop(new GainBlockAction(AbstractDungeon.player, 3));
        return super.onAttacked(info, damageAmount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
