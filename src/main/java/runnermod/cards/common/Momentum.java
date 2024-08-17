package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Overwhelm;
import runnermod.util.CardStats;

public class Momentum extends BaseCard {
    public static final String ID = makeID(Momentum.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 0;

    public Momentum()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC,MAGIC_UPG);
    }
    @Override
    public void upgrade() {
        super.upgrade();
    }






    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,this.baseDamage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DamageAction(m,new DamageInfo(p,this.baseDamage, DamageInfo.DamageType.NORMAL)));
        addToBot(new ApplyPowerAction(m,p,new Overwhelm(m,this.magicNumber)));
    }
}
