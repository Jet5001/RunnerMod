package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class ScrapRun extends BaseCard {
    public static final String ID = makeID(ScrapRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 0;
    private static final int MAG = 1;
    private static final int MAG_UPG = 1;
    public ScrapRun()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(RunnerCharacter.Enums.RUN);
        setMagic(MAG,MAG_UPG);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //put a quickRunAction into the buffer
        //added new action as specifics of damage dealt not done at this scope.
        for (int i = 0; i < 2; i++) {
            addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }

        addToBot(new BetterDiscardPileToHandAction(magicNumber));
    }
}
