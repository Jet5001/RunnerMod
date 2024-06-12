package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import runnermod.cards.BaseCard;
import runnermod.powers.EvasiveManoeuvresPower;
import runnermod.util.CardStats;


public class Apex extends BaseCard {


    public static final String ID = makeID(Apex.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            1
    );

//    //Card Stats

    private static final int MAG = 30;
    private static final int MAG_UPG =0;
//

    public Apex()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        upgradeBaseCost(0);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new VigorPower(p,magicNumber)));
        addToBot(new PressEndTurnButtonAction());
    }
}
