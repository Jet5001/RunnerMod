package runnermod.cards.rare;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class Akira extends BaseCard {
    public static final String ID = makeID(Akira.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            3
    );

//    //Card Stats
    private static final int MAGIC = 99;
    private static final int UPG_MAGIC =0;

    public Akira()
    {
        super(ID,info);
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC, UPG_MAGIC);
        this.tags.add(BaseModCardTags.FORM);
        setInnate(false,true);
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        super.upgrade();
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToTop(new ChangeRunnerStanceAction("AKIRA",0));
    }
}
