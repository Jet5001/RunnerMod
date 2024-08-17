package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Improvise extends BaseCard {
    public static final String ID = makeID(Improvise.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0

    );

    private static int MAG = 1;
    private static int MAG_UPG = 1;
    public Improvise()
    {
        super(ID,info);
        setExhaust(true);
        setMagic(MAG,MAG_UPG);
    }




    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardHandAction(p,p));
        addToBot(new ScryAction(3));
        addToBot(new DrawCardAction(3));
        addToBot(new GainEnergyAction(magicNumber));
    }
}
