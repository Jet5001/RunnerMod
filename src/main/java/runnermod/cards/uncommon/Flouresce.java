package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Flouresce extends BaseCard {
    public static final String ID = makeID(Flouresce.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    //Card Stats
    private static int MAG = 3;
    private static int MAG_UPG = 2;


    public Flouresce()
    {
        super(ID,info);
        setMagic(MAG, MAG_UPG);
        this.exhaust = true;
        this.tags.add(RunnerCharacter.Enums.NEON);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCreature mon: AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mon,p, new StrengthPower(mon,-magicNumber)));
            addToBot(new ApplyPowerAction(mon,p, new GainStrengthPower(mon,magicNumber)));
        }

    }
}
