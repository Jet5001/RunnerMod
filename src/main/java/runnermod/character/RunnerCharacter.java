package runnermod.character;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import runnermod.cards.starter.CheapShot;
import runnermod.cards.starter.Defend_Runner;
import runnermod.cards.starter.Strike_Runner;
import runnermod.cards.starter.Pilfer;
import runnermod.relics.ICEPick;

import java.util.ArrayList;

import static runnermod.RunnerMod.characterPath;
import static runnermod.RunnerMod.makeID;

public class RunnerCharacter extends CustomPlayer implements OnCardUseSubscriber, OnPlayerTurnStartSubscriber, PostBattleSubscriber {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    //Strings
    private static final String ID = makeID("Runner"); //This should match whatever you have in the CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    //Image file paths
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.

    public int runCardsPlayed = 0;
    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (abstractCard.hasTag(Enums.RUN))
        {
            runCardsPlayed +=1;
        }
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        runCardsPlayed = 0;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        img = baseImg;
        runCardsPlayed = 0;
    }

    public static class Enums {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass RUNNER;
        @SpireEnum(name = "RUNNERPURPLE") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "RUNNERPURPLE") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        @SpireEnum public static AbstractCard.CardTags NEON;
        @SpireEnum public static AbstractCard.CardTags RUN;
        @SpireEnum public static AbstractCard.CardTags CONSERVE_VIGOUR;
    }
    public Texture baseImg;
    public Texture bladesStanceImg;
    public Texture shieldsStanceImg;
    public Texture overclockerStanceImg;
    public Texture firewallStanceImg;
    public Texture berserkerStanceImg;
    public Texture blasterStanceImg;
    public Texture hackStanceImg;
    public Texture tinkerStanceImg;
    public Texture metalStanceImg;
    public Texture accelStanceImg;
    public RunnerCharacter() {
        super(NAMES[0], Enums.RUNNER,
                new CustomEnergyOrb(null, null, null), //Energy Orb
                //new SpriterAnimation(characterPath("animation/default.scml"))); //Animation
                new AbstractAnimation() {
                    @Override
                    public Type type() {
                        return Type.NONE;
                    }
                });

                initializeClass(characterPath("CharDefault.png"),
                        SHOULDER_2,
                        SHOULDER_1,
                        CORPSE,
                        getLoadout(),
                        20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                        new EnergyManager(ENERGY_PER_TURN));
        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 60f * Settings.scale);
        dialogY = (drawY + 235.0F * Settings.scale);
        BaseMod.subscribe(this);
        baseImg = img;
        bladesStanceImg = ImageMaster.loadImage(characterPath("Pose_Blades.png"));
        shieldsStanceImg = ImageMaster.loadImage(characterPath("Pose_Shields.png"));
        overclockerStanceImg = ImageMaster.loadImage(characterPath("Pose_Overclocker.png"));
        firewallStanceImg = ImageMaster.loadImage(characterPath("Pose_Firewall.png"));
        berserkerStanceImg = ImageMaster.loadImage(characterPath("Pose_Beserker_Crackling.png"));
        blasterStanceImg = ImageMaster.loadImage(characterPath("Pose_Blaster.png"));
        hackStanceImg = ImageMaster.loadImage(characterPath("Pose_Hack.png"));
        tinkerStanceImg = ImageMaster.loadImage(characterPath("Pose_Tinker.png"));
        metalStanceImg = ImageMaster.loadImage(characterPath("Pose_Metal.png"));
        accelStanceImg = ImageMaster.loadImage(characterPath("Pose_Accel.png"));
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike_Runner.ID);
        retVal.add(Strike_Runner.ID);
        retVal.add(Strike_Runner.ID);
        retVal.add(Strike_Runner.ID);
        retVal.add(Defend_Runner.ID);
        retVal.add(Defend_Runner.ID);
        retVal.add(Defend_Runner.ID);
        retVal.add(Defend_Runner.ID);
        retVal.add(Pilfer.ID);
        retVal.add(CheapShot.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(ICEPick.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Strike_Red();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }
    @Override
    public String getVampireText() {
        return TEXT[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new RunnerCharacter();
    }



}
