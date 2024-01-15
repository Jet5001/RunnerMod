package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

import java.util.Random;

public class Mending extends BaseCard {
    public static final String ID = makeID(Mending.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 1;

    public Mending()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        tags.add(RunnerCharacter.Enums.NEON);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new RepairAction(magicNumber));
    }
}
