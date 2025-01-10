package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.LaughingManPower;
import runnermod.util.CardStats;

public class LaughingMan extends BaseCard {
    public static final String ID = makeID(LaughingMan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            3

    );

    private static final int MAGIC = 4;
    private static final int MAGIC_UPG = 0;


    public LaughingMan()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        setEthereal(true,false);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new LaughingManPower(p, magicNumber)));
    }
}
