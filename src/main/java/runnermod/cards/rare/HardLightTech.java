package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.HardLightPower;
import runnermod.util.CardStats;

public class HardLightTech extends BaseCard {
    public static final String ID = makeID(HardLightTech.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            1

    );

    private static final int MAGIC = 3;
    private static final int MAGIC_UPG = 1;


    public HardLightTech()
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
            super.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new HardLightPower(p, magicNumber)));
    }
}
