package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Bankroll;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Withdrawal extends BaseCard {
    public static final String ID = makeID(Investments.class.getSimpleName());
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
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new MakeTempCardInDiscardAction(new Bankroll(),1));
    }
}