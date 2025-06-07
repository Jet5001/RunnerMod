package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class ScoutingRun extends BaseCard {
    public static final String ID = makeID(ScoutingRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    //Card Stats

    private static final int MAG = 3;
    private static final int MAG_UPG = 1;
    public ScoutingRun()
    {
        super(ID,info);
        tags.add(RunnerCharacter.Enums.RUN);
        setMagic(MAG,MAG_UPG);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //put a quickRunAction into the buffer
        //added new action as specifics of damage dealt not done at this scope.
        addToBot(new DrawCardAction(magicNumber));
        if (upgraded)
        {
            addToBot(new ScoutingRunShuffleAction(magicNumber -2));
        }
        else
        {
            addToBot(new ScoutingRunShuffleAction(magicNumber-1));
        }

    }
}
