package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import runnermod.cards.BaseCard;
import runnermod.util.CardStats;


public class RegenFactor extends BaseCard {


    public static final String ID = makeID(RegenFactor.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            1
    );

//    //Card Stats

    private static final int MAG = 10;
    private static final int MAG_UPG = 5;
//

    public RegenFactor()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new RegenPower(p,magicNumber)));
    }
}
