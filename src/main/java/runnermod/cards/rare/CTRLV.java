package runnermod.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.common.DigitiseAction;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class CTRLV extends BaseCard {
    public static final String ID = makeID(CTRLV.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

//    //Card Stats
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC =1;

    public CTRLV()
    {
        super(ID,info);
        setSelfRetain(false,true);
    }


    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PasteAction());
    }
}
