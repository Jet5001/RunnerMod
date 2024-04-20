package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Bankroll extends BaseCard {
    public static final String ID = makeID(Bankroll.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

//    //Card Stats
//    private static final int DAMAGE = 10;
//    private static final int UPG_DAMAGE =5;
//
//    private static final int MAGIC = 15;
//    private static final int UPG_MAGIC =5;
    private static int extraDamage = 0;

    public Bankroll()
    {
        super(ID,info);
    }

    @Override
    public void applyPowers()
    {
        this.baseDamage =  AbstractDungeon.player.gold/10;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            extraDamage=6;
            super.upgrade();
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,damage + extraDamage,DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
