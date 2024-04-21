package runnermod.cards.rare;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.WallOfStaticPower;
import runnermod.util.CardStats;

public class Adapt extends BaseCard {
    public static final String ID = makeID(Adapt.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            1

    );


    public int strAmount = 0;
    public int dexAmount = 1;

    public Adapt()
    {
        super(ID,info);
        this.upgradesDescription = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        initializeDescription();

    }

    @Override
    public void upgrade()
    {
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        if (timesUpgraded%2==0)
        {
            dexAmount +=1;
        }
        else
        {
            strAmount+=1;
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (strAmount > 0)
        {
            addToBot(new ApplyPowerAction(p,p,new StrengthPower(p, strAmount)));
        }
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p, dexAmount)));
    }
}

