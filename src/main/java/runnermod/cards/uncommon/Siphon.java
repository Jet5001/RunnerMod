package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.ScrapArmourPower;
import runnermod.util.CardStats;

public class Siphon extends BaseCard {
    public static final String ID = makeID(Siphon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static int MAG = 2;
    private static int MAG_UPG = 1;

    public Siphon()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        setExhaust(true);
        tags.add(RunnerCharacter.Enums.NEON);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p,new StrengthPower(m,-magicNumber)));
    }




}
