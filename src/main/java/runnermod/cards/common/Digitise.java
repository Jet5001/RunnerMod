package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Bolt;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Digitise extends BaseCard {
    public static final String ID = makeID(Digitise.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
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
    }



    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DigitiseAction());
        }

    }
}
