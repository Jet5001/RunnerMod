package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class DDOS extends BaseCard {
    public static final String ID = makeID(DDOS.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DMG = 6;
    private static final int DMG_UPG = 3;
    private static final int MAG = 1;
    private static final int MAG_UPG = 1;
    public DDOS()
    {
        super(ID,info);
        setDamage(DMG,DMG_UPG);
        setMagic(MAG,MAG_UPG);
    }


    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new DDOSAction(p,  new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL),magicNumber));
    }
}
