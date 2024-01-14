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
    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;
    public HeadOn()
    {
        super(ID,info);
        setBlock(BLOCK, BLOCK_UPG);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPower dex = AbstractDungeon.player.getPower("Dexterity");
        int originalStr = 0;
        int originalDex = 0;
        if (strength != null)
        {
            originalStr = strength.amount;
        }
        if (dex!=null)
        {
            originalDex = dex.amount;
        }
        this.block = this.block + originalStr - originalDex;
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
        addToBot(new GainBlockAction(p,p,this.block));
    }
}
