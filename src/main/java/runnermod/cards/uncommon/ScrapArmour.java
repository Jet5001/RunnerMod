package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.BotNetPower;
import runnermod.powers.ScrapArmourPower;
import runnermod.util.CardStats;

import java.util.Random;

public class ScrapArmour extends BaseCard {
    public static final String ID = makeID(ScrapArmour.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static int MAG = 4;
    private static int MAG_UPG = 0;

    public ScrapArmour()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        this.setInnate(false, true);
    }

    @Override
    public void upgrade() {
        super.upgrade();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ScrapArmourPower(p,4)));
    }




}
