package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import runnermod.cards.BaseCard;
import runnermod.cards.RandomDebuffAction;
import runnermod.powers.AutopilotPower;
import runnermod.util.CardStats;


public class Autopilot extends BaseCard {


    public static final String ID = makeID(Autopilot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            2
    );

//    //Card Stats

    private static final int MAG = 3;
    private static final int MAG_UPG =1;
//

    public Autopilot()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new AutopilotPower(p,magicNumber)));
    }
}
