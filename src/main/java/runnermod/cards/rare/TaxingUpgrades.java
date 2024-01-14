package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.InvestmentsPower;
import runnermod.powers.TaxingUpgradesPower;
import runnermod.util.CardStats;

public class TaxingUpgrades extends BaseCard {
    public static final String ID = makeID(TaxingUpgrades.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2

    );

    public TaxingUpgrades()
    {
        super(ID,info);
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
        addToBot(new ApplyPowerAction(p,p,new TaxingUpgradesPower(p, 1)));
    }
}
