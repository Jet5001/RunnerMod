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


    public Adapt()
    {
        super(ID,info);
        this.upgradesDescription = true;
        this.setMagic(1);
        this.setCustomVar("DEX",1,0);
        initializeDescription();
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
        upgradeMagicNumber(1);
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p, customVar("DEX"))));
    }
}

