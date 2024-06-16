package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import runnermod.cards.BaseCard;
import runnermod.cards.RandomDebuffAction;
import runnermod.util.CardStats;


public class TheWeaknessOfFlesh extends BaseCard {


    public static final String ID = makeID(TheWeaknessOfFlesh.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            1
    );

//    //Card Stats

    private static final int MAG = 20;
    private static final int MAG_UPG =5;
//

    public TheWeaknessOfFlesh()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new PlatedArmorPower(p,magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,-5)));
    }
}
