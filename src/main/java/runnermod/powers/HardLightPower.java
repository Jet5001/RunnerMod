package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class HardLightPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("HardLightPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public HardLightPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(RunnerCharacter.Enums.NEON))
        {
            card.damage +=amount;
        }
        super.onPlayCard(card, m);
    }

    @Override
    public AbstractPower makeCopy() {
        return new HardLightPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount;
    }

}
