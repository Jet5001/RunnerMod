package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class EMP extends BaseCard {
    public static final String ID = makeID(EMP.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    //    //Card Stats
//    private static final int DAMAGE = 10;
//    private static final int UPG_DAMAGE =5;
//
    private static final int MAGIC = 10;
    private static final int UPG_MAGIC =0;

    public EMP()
    {
        super(ID,info);
        this.exhaust = true;
    }


    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.retain = true;
            super.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new RemoveAllBlockAction(mo,p));
        }
    }
}
