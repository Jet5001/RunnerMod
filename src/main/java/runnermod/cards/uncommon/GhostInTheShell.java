package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.rare.LostInTheCrowdAction;
import runnermod.cards.tempcards.Decoy;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class GhostInTheShell extends BaseCard {
    public static final String ID = makeID(GhostInTheShell.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1

    );

    private static int MAG = 2;
    private static int MAG_UPG = 1;

    private static int BLOCK = 5;
    public GhostInTheShell() {
        super(ID, info);
        setMagic(MAG,MAG);
        setBlock(BLOCK);
        this.cardsToPreview = new Decoy();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        addToBot(new MakeTempCardInHandAction(new Decoy(),this.magicNumber));
    }
}