package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.PawnShopPower;
import runnermod.util.CardStats;

public class PawnShop extends BaseCard {
    public static final String ID = makeID(PawnShop.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2

    );

    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 0;


    public PawnShop()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        setEthereal(true,false);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new PawnShopPower(p, magicNumber)));
    }
}
