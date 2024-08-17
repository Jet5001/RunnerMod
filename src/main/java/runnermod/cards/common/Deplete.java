package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.DepletedPower;
import runnermod.powers.Hacked;
import runnermod.util.CardStats;

public class Deplete extends BaseCard {
    public static final String ID = makeID(Deplete.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    private static final int MAG = 2;
    private static final int MAG_UPG = 0;
    public Deplete()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.tags.add(RunnerCharacter.Enums.NEON);
    }

    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.gainEnergy(magicNumber);
        addToBot(new ApplyPowerAction(p,p,new DepletedPower(p,1)));
    }
}
