package runnermod.cards.tempcards;

import basemod.helpers.CardTags;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;




public class Bolt extends BaseCard {


    public static final String ID = makeID(Bolt.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

//    //Card Stats
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE =3;
//

    public Bolt()
    {
        super(ID,info);
        setDamage(DAMAGE,UPG_DAMAGE);
        tags.add(RunnerCharacter.Enums.NEON);
        this.exhaust = true;
    }




    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,damage ,DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
    }
}
