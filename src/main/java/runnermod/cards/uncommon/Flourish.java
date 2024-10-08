package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Flourish extends BaseCard {
    public static final String ID = makeID(Flourish.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 0;
    private static final int MAG = 2;
    private static final int MAG_UPG = 1;

    public Flourish()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAG,MAG_UPG);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        int originalStr = 0;
        int originalDex = 0;
        int originalVitality = 0;
        int originalVigor = 0;
        if (strength != null)
        {
            originalStr = strength.amount;
        }
        AbstractPower dex = AbstractDungeon.player.getPower("Dexterity");
        if (dex!=null)
        {
            originalDex = dex.amount;
        }
        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        if (vigor!=null)
        {
            originalVigor = vigor.amount;
        }
        AbstractPower vitality = AbstractDungeon.player.getPower("RunnerMod:VitalityPower");
        if (vitality!=null)
        {
            originalVitality = vitality.amount;
        }
        this.damage = this.damage + originalDex - originalStr - originalVigor + originalVitality;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        int originalStr = 0;
        int originalDex = 0;
        if (strength != null)
        {
            originalStr = strength.amount;
        }

        AbstractPower dex = AbstractDungeon.player.getPower("Dexterity");
        if (dex!=null)
        {
            originalDex = dex.amount;
        }
        if (strength != null)
        {
            strength.amount = 0;
        }
        if (dex != null)
        {
            dex.amount = 0;
        }
        super.applyPowers();
        if (strength != null)
        {
            strength.amount = originalStr;
        }
        if (dex != null)
        {
            dex.amount = originalDex;
        }
    }

    //Not applying vulnerable before damage for some reason?
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

    }
}
