package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import runnermod.cards.BaseCard;
import runnermod.util.CardStats;


public class WallOfForce extends BaseCard {


    public static final String ID = makeID(WallOfForce.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            2
    );

//    //Card Stats

    private static int BLOCK = 50;
    private static int BLOCK_UPG = 10;
//

    public WallOfForce()
    {
        super(ID,info);
        setBlock(50,10);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new ApplyPowerAction(p,p,new BlurPower(p,5)));
        addToBot(new ApplyPowerAction(p,p,new NoBlockPower(p,5,false)));
    }
}
