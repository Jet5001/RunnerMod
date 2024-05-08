package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import jdk.nashorn.internal.ir.Block;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class Cloaking extends BaseCard {
    public static final String ID = makeID(Cloaking.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Cloaking()
    {
        super(ID,info);
        setMagic(MAGIC,UPG_MAGIC);
        setExhaust(true);
    }


    //Not applying vulnerable before damage for some reason?
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new BlockReturnPower(m, this.magicNumber),1, true, AbstractGameAction.AttackEffect.NONE));
        addToTop(new ChangeRunnerStanceAction("Wall",5));
    }
}
