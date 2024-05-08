package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Decoy;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class DoubleTime extends BaseCard {
    public static final String ID = makeID(DoubleTime.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 5;
    private static final int UPG_MAGIC =0;

    public DoubleTime()
    {
        super(ID,info);
        this.setMagic(MAGIC, UPG_MAGIC);
        this.tags.add(RunnerCharacter.Enums.NEON);
        this.cardsToPreview = new Decoy();
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
            addToBot(new MakeTempCardInHandAction(new Decoy(),2));
        }
        else{
            addToBot(new MakeTempCardInHandAction(new Decoy(), 3));
        }
    }
}
