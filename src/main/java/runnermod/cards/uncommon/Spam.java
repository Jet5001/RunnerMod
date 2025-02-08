package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Spam extends BaseCard {
    public static final String ID = makeID(Spam.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static int DMG = 8;
    private static int DMG_UP = 3;
    private static int MAG = 1;
    private static int MAG_UPG = 0;

    public Spam()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        setDamage(DMG,DMG_UP);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DrawCardAction(1));
            addToBot(new SpamDiscountAction());
        }


    }




}
