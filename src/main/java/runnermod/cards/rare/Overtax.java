package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.OvertaxPower;
import runnermod.powers.WallOfStaticPower;
import runnermod.util.CardStats;

public class Overtax extends BaseCard {
    public static final String ID = makeID(Overtax.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            1

    );

    private static final int MAGIC = -2;
    private static final int MAGIC_UPG = 1;


    public Overtax()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
    }


    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.isInnate = true;
            super.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new OvertaxPower(p, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player, this.magicNumber)));
    }
}
