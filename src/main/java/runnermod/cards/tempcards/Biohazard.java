package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import runnermod.cards.BaseCard;
import runnermod.cards.RandomDebuffAction;
import runnermod.powers.EvasiveManoeuvresPower;
import runnermod.util.CardStats;


public class Biohazard extends BaseCard {


    public static final String ID = makeID(Biohazard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            1
    );

//    //Card Stats

    private static final int MAG = 15;
    private static final int MAG_UPG =5;
//

    public Biohazard()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new RandomDebuffAction(p, AbstractDungeon.getRandomMonster()));
        }
        addToBot(new ApplyPowerAction(p,p,new PoisonPower(p,p,5)));
    }
}
