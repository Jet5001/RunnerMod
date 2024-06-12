package runnermod.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostPowerApplySubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.tempcards.Interest;

import java.io.Console;

import static runnermod.RunnerMod.makeID;

public class Hacked extends BasePower implements CloneablePowerInterface, PostPowerApplySubscriber {


    public static final String POWER_ID = makeID("Hacked");
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = true;
    public boolean fromLaughingMan = false;
    public Hacked(AbstractCreature owner, int hackAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, hackAmount);
    }

    public Hacked(AbstractCreature owner, int hackAmount, boolean fromLaughingMan)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, hackAmount);
        this.fromLaughingMan = fromLaughingMan;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new ReducePowerAction(owner,owner,Hacked.POWER_ID,1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new InvestmentsPower(owner, amount);
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        System.out.println("ApplyingPower");
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && source != this.owner && target == this.owner) {
            flash();
            addToBot(new DamageAction(target, new DamageInfo(source, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
        }
    }
}
