package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
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
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 0;
    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 0;
    int debuffCount = 0;
    public SystemCrash()
    {
        super(ID,info);
        //setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }
    AbstractCreature latestTarget;
    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBaseCost(1);
    }

    public void calculateCardDamage(AbstractMonster mo) {

        debuffCount = 0;
        for (AbstractPower pow :mo.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF)
            {
                debuffCount ++;
            }
        }
        this.baseDamage = debuffCount * this.magicNumber;
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
        latestTarget = mo;
    }

    public void applyPowers() {
        debuffCount = 0;
        if (latestTarget!= null)
        {
            for (AbstractPower pow :latestTarget.powers) {
                if (pow.type == AbstractPower.PowerType.DEBUFF)
                {
                    debuffCount ++;
                }
            }
        }
        this.baseDamage = debuffCount * this.magicNumber;
        if (debuffCount > 0) {
            this.baseDamage = debuffCount * this.magicNumber;
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        debuffCount = 0;
        for (AbstractPower pow :m.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF)
            {
                debuffCount ++;
            }
        }
        this.baseDamage = debuffCount * this.magicNumber;
        System.out.println("Debuff Count: " + debuffCount + "  BaseDamage: " + this.baseDamage);
        addToBot(new DamageAction(m,new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
