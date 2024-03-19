package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.BotNetPower;
import runnermod.util.CardStats;

import java.util.Random;

public class Bide extends BaseCard {
    public static final String ID = makeID(Bide.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );


    private static int BLOCK = 10;
    private static int BLOCK_UPG = -4;

    public Bide()
    {
        super(ID,info);
        setBlock(BLOCK,BLOCK_UPG);
        tags.add(RunnerCharacter.Enums.NEON);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(m,block));
        addToBot(new GainEnergyAction(2));
    }



}
