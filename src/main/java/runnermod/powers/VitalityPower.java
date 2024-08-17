package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.character.RunnerCharacter;

import javax.smartcardio.Card;

import static runnermod.RunnerMod.makeID;

public class VitalityPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("VitalityPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public VitalityPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        flash();
        blockAmount += this.amount;
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, this));
        addToBot(new GainBlockAction(AbstractDungeon.player, amount));
        super.onGainedBlock(blockAmount);
    }


    @Override
    public AbstractPower makeCopy() {

        return new VitalityPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
