package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import runnermod.cards.BaseCard;
import runnermod.cards.RandomDebuffAction;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;


public class SpaceLaser extends BaseCard {


    public static final String ID = makeID(SpaceLaser.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

//    //Card Stats
    private static final int DAMAGE = 50;
    private static final int UPG_DAMAGE = 100;

    private static final int MAG = 10;
    private static final int MAG_UPG = 10;
//

    public SpaceLaser()
    {
        super(ID,info);
        setDamage(DAMAGE,UPG_DAMAGE);
        setMagic(MAG,MAG_UPG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,damage ,DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new ApplyPowerAction(p,p,new PoisonPower(p,p,magicNumber)));
    }
}
