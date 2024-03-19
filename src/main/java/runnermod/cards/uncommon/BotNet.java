package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.RandomDebuffAction;
import runnermod.character.RunnerCharacter;
import runnermod.powers.BotNetPower;
import runnermod.util.CardStats;

import java.util.Random;

public class BotNet extends BaseCard {
    public static final String ID = makeID(BotNet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    Random rng;

    private static int MAG = 1;
    private static int MAG_UPG = 2;

    public BotNet()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new BotNetPower(p,magicNumber)));
    }



}
