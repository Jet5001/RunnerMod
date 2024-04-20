package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.Unused.BladeStrike;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class MultiLayerShield extends BaseCard {
    public static final String ID = makeID(MultiLayerShield.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1

    );
    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 0;
    public MultiLayerShield()
    {
        super(ID,info);
        setBlock(BLOCK, BLOCK_UPG);
    }

    @Override
    public void upgrade() {
        upgradeBlock(5 + this.timesUpgraded);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
        super.upgrade();
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
    }
}