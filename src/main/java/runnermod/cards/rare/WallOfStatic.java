package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.InvestmentsPower;
import runnermod.powers.WallOfStaticPower;
import runnermod.util.CardStats;

public class WallOfStatic extends BaseCard {
    public static final String ID = makeID(WallOfStatic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2

    );

    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 1;


    public WallOfStatic()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new WallOfStaticPower(p, magicNumber)));
    }
}
