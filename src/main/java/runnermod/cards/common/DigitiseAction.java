package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import runnermod.RunnerMod;
import runnermod.character.RunnerCharacter;

import java.util.ArrayList;

public class DigitiseAction extends AbstractGameAction{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DigitiseAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

    private boolean upgraded = false;

    public DigitiseAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (c.hasTag(RunnerCharacter.Enums.NEON))
                    this.cannotUpgrade.add(c);
            }
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                    if (!c.hasTag(RunnerCharacter.Enums.NEON)) {
                        c.tags.add(RunnerCharacter.Enums.NEON);
                        c.rawDescription += " NL RunnerMod:Neon";
                        c.initializeDescription();
                        c.superFlash();
                        c.applyPowers();
                        this.isDone = true;
                        return;
                    }
                }
            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                this.p.hand.getTopCard().tags.add(RunnerCharacter.Enums.NEON);
                this.p.hand.getTopCard().rawDescription += " NL RunnerMod:Neon";
                this.p.hand.getTopCard().initializeDescription();
                this.p.hand.getTopCard().superFlash();
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c.tags.add(RunnerCharacter.Enums.NEON);
                c.rawDescription += " NL RunnerMod:Neon";
                c.initializeDescription();
                c.superFlash();
                c.applyPowers();
                this.p.hand.addToTop(c);
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
        this.isDone = true;
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotUpgrade)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

