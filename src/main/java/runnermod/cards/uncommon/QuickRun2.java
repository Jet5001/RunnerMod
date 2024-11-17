package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class QuickRun2 extends BaseCard {
    public static final String ID = makeID(QuickRun2.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    //Card Stats
    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 3;

    public QuickRun2()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(RunnerCharacter.Enums.RUN);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //put a quickRunAction into the buffer
        //added new action as specifics of damage dealt not done at this scope.
        addToBot(new DamageAction(m, new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >=3)
        {
            addToBot(new GainEnergyAction(2));
        }
    }
}
