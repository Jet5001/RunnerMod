package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Stockpile extends BaseCard {
    public static final String ID = makeID(Stockpile.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 15;
    private static final int UPG_MAGIC =0;

    public Stockpile()
    {
        super(ID,info);
        this.exhaust = true;
        this.isEthereal = true;
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC, UPG_MAGIC);
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            //remove Ethereal on upgrade
            this.isEthereal = false;
            super.upgrade();
        }
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToBot(new GainGoldAction(magicNumber));
    }
}
