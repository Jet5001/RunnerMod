package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class NeonBlades extends BaseCard {
    public static final String ID = makeID(NeonBlades.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1

    );
    private static final int DMG = 2;
    private static final int DMG_UPG = 0;
    private static final int MAGIC = 5;
    private static final int MAGIC_UPG = 0;
    public NeonBlades()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        setDamage(DMG,DMG_UPG);
        tags.add(RunnerCharacter.Enums.NEON);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeRunnerStanceAction("Blades",magicNumber));
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
