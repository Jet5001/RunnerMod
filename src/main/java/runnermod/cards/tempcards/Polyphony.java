package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Polyphony extends BaseCard {
    public static final String ID = makeID(Polyphony.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2
    );

    //Card Stats
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 0;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 0;
    int cardsPlayed = 0;
    public Polyphony()
    {
        super(ID,info);
        //setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }
    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBaseCost(1);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        if (AbstractDungeon.player instanceof RunnerCharacter)
        {
            cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        }
        this.baseDamage = cardsPlayed * this.magicNumber;
        super.calculateCardDamage(mo);
    }


    public void applyPowers() {
        cardsPlayed = 0;
        if (AbstractDungeon.player instanceof RunnerCharacter)
        {
            cardsPlayed =AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        }

        this.baseDamage = (cardsPlayed +1) * this.magicNumber;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
        super.applyPowers();
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof RunnerCharacter)
        {
            cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        }

        this.baseDamage = cardsPlayed * this.magicNumber;
        addToBot(new DamageAllEnemiesAction(p, this.baseDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
    }
}
