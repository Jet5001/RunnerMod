package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Bolt;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Charge extends BaseCard {
    public static final String ID = makeID(Charge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC =1;

    public Charge()
    {
        super(ID,info);
        this.setMagic(MAGIC, UPG_MAGIC);
        this.cardsToPreview = new Bolt();
    }



    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new MakeTempCardInHandAction(new Bolt(), magicNumber));
    }
}
