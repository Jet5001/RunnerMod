package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.InvestmentsPower;
import runnermod.powers.TaxingUpgradesPower;
import runnermod.util.CardStats;

public class DefensiveArmourments extends BaseCard {
    public static final String ID = makeID(DefensiveArmourments.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1

    );

    private static final int BLOCK = 2;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;
    public DefensiveArmourments()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        this.setBlock(BLOCK,BLOCK_UPG);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p, magicNumber)));
        addToBot(new GainBlockAction(p,p,this.block));
    }
}
