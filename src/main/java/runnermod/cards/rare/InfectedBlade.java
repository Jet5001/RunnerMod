package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Bankroll;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Hacked;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class InfectedBlade extends BaseCard {
    public static final String ID = makeID(InfectedBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3

    );

    private static final int DMG = 6;
    private static final int DMG_UPG  = 3;
    private static final int MAG = 3;
    private static final int MAG_UPG = 1;

    public InfectedBlade() {
        super(ID, info);
        setDamage(DMG,DMG_UPG);
        setMagic(MAG,MAG_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m,p,new Hacked(m,3)));
        addToBot(new ChangeRunnerStanceAction("Blades",5));
    }
}