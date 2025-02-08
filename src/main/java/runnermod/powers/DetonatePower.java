package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class DetonatePower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("DetonatePower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public DetonatePower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p,new DamageInfo(p,this.amount, DamageInfo.DamageType.THORNS)));
    }

    @Override
    public AbstractPower makeCopy() {

        return new DetonatePower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
