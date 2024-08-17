package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import runnermod.character.RunnerCharacter;

import java.util.ArrayList;

public class PasteAction extends AbstractGameAction{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PasteAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

    private boolean upgraded = false;

    public PasteAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    private void dupeCard(AbstractCard c)
    {
        AbstractCard toDupe = c.makeStatEquivalentCopy();
        toDupe.costForTurn = 0;
        for (AbstractCard.CardTags tag:c.tags) {
            if (!toDupe.hasTag(tag))
            {
                toDupe.tags.add(tag);
            }
        }
        toDupe.rawDescription = c.rawDescription;
        toDupe.initializeDescription();
        addToTop(new MakeTempCardInHandAction(toDupe));
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                        dupeCard(c);
                        returnCards();
                        this.isDone = true;
                        return;
                }
            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                dupeCard(this.p.hand.getTopCard());
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                dupeCard(c);
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

