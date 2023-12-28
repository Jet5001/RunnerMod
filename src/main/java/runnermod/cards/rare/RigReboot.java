package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class RigReboot extends BaseCard {
    public static final String ID = makeID(RigReboot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );


    public RigReboot()
    {
        super(ID,info);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.retain = true;
    }

    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower power:p.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF)
            {
                addToTop(new RemoveSpecificPowerAction(p,p,power));
            }
        }
    }
}
