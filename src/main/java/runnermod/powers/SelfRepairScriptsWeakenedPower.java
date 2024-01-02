package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import runnermod.cards.tempcards.Interest;

import static runnermod.RunnerMod.makeID;

public class SelfRepairScriptsWeakenedPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("SelfRepairScriptsWeakenedPower");
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public SelfRepairScriptsWeakenedPower(AbstractCreature owner, int RepairAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, RepairAmount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        AbstractCreature player = AbstractDungeon.player;

        addToBot(new ReducePowerAction(player,player, WeakPower.POWER_ID,this.amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SelfRepairScriptsWeakenedPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
