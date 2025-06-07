package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.common.DigitiseAction;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Digitise extends BaseCard {
    public static final String ID = makeID(Digitise.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

//    //Card Stats
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC =1;

    public Digitise()
    {
        super(ID,info);
        this.setMagic(MAGIC, UPG_MAGIC);
        this.exhaust = true;
        this.tags.add(RunnerCharacter.Enums.NEON);
    }



    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new DigitiseAction(magicNumber));
    }
}
