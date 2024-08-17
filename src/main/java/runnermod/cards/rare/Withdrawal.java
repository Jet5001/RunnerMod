package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Bankroll;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

import javax.smartcardio.Card;

public class Withdrawal extends BaseCard {
    public static final String ID = makeID(Withdrawal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            1

    );

    public Withdrawal() {
        super(ID, info);
        this.exhaust = true;
        this.cardsToPreview = new Bankroll();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.cardsToPreview.upgrade();
        this.cardsToPreview.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(upgraded)
        {
            addToBot(new MakeTempCardInDiscardAction(this.cardsToPreview,1));
        }
        else
        {
            addToBot(new MakeTempCardInDiscardAction(this.cardsToPreview,1));
        }

    }
}