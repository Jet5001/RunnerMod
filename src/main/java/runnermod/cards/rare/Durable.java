package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.DurablePower;
import runnermod.powers.TaxingUpgradesPower;
import runnermod.util.CardStats;

public class Durable extends BaseCard {
    public static final String ID = makeID(Durable.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2

    );

    private static int MAG = 3;
    private static int MAG_UP = 0;
    public Durable()
    {
        super(ID,info);
        setMagic(MAG,MAG_UP);
    }


    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBaseCost(1);
            super.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DurablePower(p, magicNumber)));
    }
}
