package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Virus;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Manifest extends BaseCard {
    public static final String ID = makeID(Manifest.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1

    );

    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 1;
    public Manifest()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new MakeTempCardInDiscardAction(new Virus(),1));
    }
}
