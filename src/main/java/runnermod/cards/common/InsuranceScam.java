package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class InsuranceScam extends BaseCard {
    public static final String ID = makeID(InsuranceScam.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

//    //Card Stats
    private static final int DAMAGE = 5;
    private static final int DAMAGE_UPG = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC =4;

    public InsuranceScam()
    {
        super(ID,info);
        this.exhaust = true;
        this.setDamage(DAMAGE,DAMAGE_UPG);
        this.setMagic(MAGIC, UPG_MAGIC);
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
        }
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToBot(new DamageAction(p, new DamageInfo(p,damage, DamageInfo.DamageType.THORNS)));
        addToBot(new GainGoldAction(damage*magicNumber));
    }
}
