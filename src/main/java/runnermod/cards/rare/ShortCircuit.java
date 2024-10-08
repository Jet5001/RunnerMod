package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.ShortCircuitPower;
import runnermod.powers.WallOfStaticPower;
import runnermod.util.CardStats;

public class ShortCircuit extends BaseCard {
    public static final String ID = makeID(ShortCircuit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2

    );

    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 0;


    public ShortCircuit()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
    }


    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeBaseCost(1);
            super.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ShortCircuitPower(p, magicNumber)));
    }
}
