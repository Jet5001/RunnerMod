package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

import java.util.Random;

public class Upgrades extends BaseCard {
    public static final String ID = makeID(Upgrades.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;
    Random rng;

    public Upgrades()
    {
        super(ID,info);
        this.exhaust = true;
        rng = new Random();
        setMagic(MAGIC,MAGIC_UPG);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new UpgradeRandomCardsInHandAction(magicNumber, this));
        addToBot(new DrawCardAction(1));
    }
}
