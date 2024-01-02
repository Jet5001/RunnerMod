package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Hacked;
import runnermod.util.CardStats;

public class Hack extends BaseCard {
    public static final String ID = makeID(Hack.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAG = 3;
    private static final int MAG_UPG = 2;
    public Hack()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }


    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new ApplyPowerAction(m,p,new Hacked(m,magicNumber)));
    }
}
