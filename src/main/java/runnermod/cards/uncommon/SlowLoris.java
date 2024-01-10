package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class SlowLoris extends BaseCard {
    public static final String ID = makeID(SlowLoris.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 1;
    private static final int UPG_DAMAGE = 0;
    private static final int MAGIC = 4;
    private static final int MAGIC_UPG = 2;
    public SlowLoris()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC,MAGIC_UPG);
    }


    //Not applying vulnerable before damage for some reason?
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

    }
}
