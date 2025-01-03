package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.FlourishPower;
import runnermod.powers.ScrapArmourPower;
import runnermod.util.CardStats;

public class Flourish extends BaseCard {
    public static final String ID = makeID(Flourish.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static int MAG = 5;
    private static int MAG_UPG = 2;
    private static final int DMG = 6;
    private static final int DMG_UPG = 0;
    public Flourish()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        setDamage(DMG,DMG_UPG);
        this.setInnate(false, true);
    }

    @Override
    public void upgrade() {
        super.upgrade();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m,p,new FlourishPower(m,magicNumber)));
    }




}
