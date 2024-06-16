package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.PostPowerApplySubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class AutopilotPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("AutopilotPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public boolean fromLaughingMan = false;
    public AutopilotPower(AbstractCreature owner, int Amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, Amount);
    }


    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        for (int i = 0; i < amount; i++) {
            addToBot(new PlayTopCardAction(AbstractDungeon.getRandomMonster(),false));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AutopilotPower(owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
