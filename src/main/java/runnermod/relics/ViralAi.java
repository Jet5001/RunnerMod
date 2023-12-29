package runnermod.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.colorless.GoodInstincts;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class ViralAi extends BaseRelic{
    private static final String NAME = "ViralAi";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    public ViralAi()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_DEFEND|| m.intent == AbstractMonster.Intent.ATTACK_DEBUFF )
        {
            addToBot(new MakeTempCardInHandAction(new Defend_Blue()));
        }
        else
        {
            addToBot(new MakeTempCardInHandAction(new Strike_Red()));
        }
    }
}
