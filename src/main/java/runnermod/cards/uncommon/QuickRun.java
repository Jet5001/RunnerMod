package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class QuickRun extends BaseCard {
    public static final String ID = makeID(QuickRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public QuickRun()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //put a quickRunAction into the buffer
        //added new action as specifics of damage delt not done at this scope.
        addToBot(new QuickRunAction(m, new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
    }
}
