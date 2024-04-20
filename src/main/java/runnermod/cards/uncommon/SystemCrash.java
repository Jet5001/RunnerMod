package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class SystemCrash extends BaseCard {
    public static final String ID = makeID(SystemCrash.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    //Card Stats
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;

    public SystemCrash()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBaseCost(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int debuffCount = 0;
        for (AbstractPower pow :m.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF)
            {
                debuffCount ++;
            }
        }
        addToBot(new DamageAction(m,new DamageInfo(p,damage *debuffCount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
