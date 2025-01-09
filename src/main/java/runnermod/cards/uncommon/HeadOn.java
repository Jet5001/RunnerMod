package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class HeadOn extends BaseCard {
    public static final String ID = makeID(HeadOn.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    //Card Stats
    private static final int BLOCK = 3;
    private static final int BLOCK_UPG = 0;
    private static final int MAG = 2;
    private static final int MAG_UPG = 1;
    public HeadOn()
    {
        super(ID,info);
        setBlock(BLOCK, BLOCK_UPG);
        setMagic(MAG,MAG_UPG);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        AbstractPower dex = AbstractDungeon.player.getPower("Dexterity");
        int originalStr = 0;
        int originalDex = 0;
        int originalVigor = 0;
        if (strength != null)
        {
            originalStr = strength.amount;
        }
        if (vigor != null)
        {
            originalVigor = vigor.amount;
        }
        if (dex!=null)
        {
            originalDex = dex.amount;
        }
        this.block = this.block + originalStr - originalDex + originalVigor;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        int originalStr = 0;
        int originalVigor = 0;
        int originalDex = 0;
        if (strength != null)
        {
            originalStr = strength.amount;
        }
        if (vigor != null)
        {
            originalVigor = vigor.amount;
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
        if (vigor != null)
        {
            vigor.amount = 0;
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
        if (vigor != null)
        {
            vigor.amount = originalVigor;
        }
    }

    @Override
    public void initializeDescription() {
        if(AbstractDungeon.player == null)
        {
            super.initializeDescription();
            return;
        }
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        int originalStr = 0;
        int originalVigor = 0;
        int originalDex = 0;
        if (strength != null)
        {
            originalStr = strength.amount;
        }
        if (vigor != null)
        {
            originalVigor = vigor.amount;
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
        if (vigor != null)
        {
            vigor.amount = 0;
        }
        if (dex != null)
        {
            dex.amount = 0;
        }
        super.initializeDescription();
        if (strength != null)
        {
            strength.amount = originalStr;
        }
        if (dex != null)
        {
            dex.amount = originalDex;
        }
        if (vigor != null)
        {
            vigor.amount = originalVigor;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new GainBlockAction(p,p,this.block));
        }
    }
}
