package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class OverclockStanceSwitch extends BaseCard {
    public static final String ID = makeID(OverclockStanceSwitch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 5;
    private static final int UPG_MAGIC =0;

    public OverclockStanceSwitch()
    {
        super(ID,info);
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC, UPG_MAGIC);
        this.tags.add(RunnerCharacter.Enums.NEON);
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        super.upgrade();
        if (!this.upgraded)
        {
            upgradeName();
            this.upgraded = true;
        }
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new ChangeRunnerStanceAction("Overclock",magicNumber));
        //can't be bothered adding another number so I'm just checking
        if (this.upgraded){
            addToBot(new DrawCardAction(2));
        }
        else{
            addToBot(new DrawCardAction(1));
        }
    }
}
