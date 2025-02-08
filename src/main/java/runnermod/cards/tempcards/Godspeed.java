package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import runnermod.cards.BaseCard;
import runnermod.powers.EvasiveManoeuvresPower;
import runnermod.util.CardStats;


public class Godspeed extends BaseCard {


    public static final String ID = makeID(Godspeed.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            1
    );

//    //Card Stats

    private static final int MAG = 99;
    private static final int MAG_UPG =0;
//

    public Godspeed()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        upgradeBaseCost(0);
        setExhaust(true);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new EvasiveManoeuvresPower(p,magicNumber)));
    }
}
