package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Virus;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class FaultyUpgrade extends BaseCard {
    public static final String ID = makeID(FaultyUpgrade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1

    );

    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 1;
    public FaultyUpgrade()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        cardsToPreview = new Virus();
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p, magicNumber)));
        addToBot(new MakeTempCardInDiscardAction(new Virus(),2));
    }
}
