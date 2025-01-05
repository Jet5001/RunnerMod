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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.character.RunnerCharacter;

import javax.smartcardio.Card;

import java.lang.reflect.Field;
import java.util.Locale;

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
    public float modifyBlockLast(float blockAmount) {
        return super.modifyBlockLast(blockAmount + this.amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //since modify block happens before this call this amount is added automatically
        //the if below is true when block value of card >0 base (-1 if not set) so is likely meant to add block
        if(card.baseBlock > 0)
        {
            flash();
            addToTop(new RemoveSpecificPowerAction(this.owner,this.owner, this));
            super.onUseCard(card, action);
        }

    }

    @Override
    public AbstractPower makeCopy() {

        return new VitalityPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
