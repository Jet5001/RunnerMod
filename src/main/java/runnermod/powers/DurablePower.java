package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Locale;

import static runnermod.RunnerMod.makeID;

public class DurablePower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("DurablePower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public DurablePower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.rawDescription.toLowerCase().contains("equip") || card.rawDescription.contains("RunnerMod:Equip"))
        {
            card.magicNumber +=amount;
        }
        super.onPlayCard(card, m);
    }

    @Override
    public AbstractPower makeCopy() {
        return new DurablePower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
