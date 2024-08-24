package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class EndOfTheLine extends BaseCard {
    public static final String ID = makeID(EndOfTheLine.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 40;
    private static final int UPG_DAMAGE = 10;

    public EndOfTheLine()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.exhaust = true;
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //put a quickRunAction into the buffer
        //added new action as specifics of damage dealt not done at this scope.
        addToBot(new DamageAction(m, new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (!p.hasPower("Artifact"))
        {
            addToBot(new ApplyPowerAction(p,p, new EndTurnDeathPower(p)));
        }
        else
        {
            p.getPower("Artifact").flash();
        }

    }
}
