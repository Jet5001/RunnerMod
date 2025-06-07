package runnermod;

import java.io.Console;
import java.util.HashMap;
import java.util.Properties;

import basemod.BaseMod;
import basemod.ConsoleTargetedPower;
import basemod.abstracts.CustomMultiPageFtue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;

import runnermod.character.RunnerCharacter;
import runnermod.stances.*;
import runnermod.util.LocalizedRunnerStanceStrings;
import runnermod.util.LocalizedRunnerTutorialStrings;
import runnermod.util.TextureLoader;

import static runnermod.RunnerMod.*;

public class EquipmentRenderer{
    private static final Color textColor = Settings.CREAM_COLOR.cpy();
    private static final Color alphaColor = new Color(1.0F, 1.0F, 1.0F, 0.5F);
    private static final float fontScale = 0.7F;
    private static final float scale = 0.6F * Settings.scale;
    private static final float cubeScale = scale * 1.15F;
    private static float NUM_X_OFFSET;
    private static float NUM_Y_OFFSET;

    private SpireConfig hasSeenEquipmentTutorialConfig;
    private boolean hasSeenEquipmentTutorial;

    public static Texture accelTexture, berserkerTexture, bladesTexture, blasterTexture, firewallTexture, hackTexture,
            metalTexture, overclockTexture, shieldsTexture, tinkerTexture, cubeTexture, glitchedCubeTexture, glitchedCircleTexture, circleTexture,
            glitchedSmallCircleTexture, smallCircleTexture, tutorial1,tutorial2,tutorial3;

    public static Hitbox accelHB, berserkerHB, bladesHB, blasterHB, firewallHB, hackHB, metalHB, overclockHB,
            shieldsHB, tinkerHB;

    public static HashMap<String, DisplayEquipment> equipment = new HashMap<>();
    public EquipmentRenderer() {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;

        cubeTexture = TextureLoader.getTexture(imagePath("equipment/Cube.png"));
        circleTexture = TextureLoader.getTexture(imagePath("equipment/Circle.png"));
        smallCircleTexture = TextureLoader.getTexture(imagePath("equipment/Circle_small.png"));
        glitchedCubeTexture = TextureLoader.getTexture(imagePath("equipment/GlitchedCube.png"));
        glitchedCircleTexture = TextureLoader.getTexture(imagePath("equipment/GlitchedCircle.png"));
        glitchedSmallCircleTexture = TextureLoader.getTexture(imagePath("equipment/GlitchedCircle_small.png"));

        accelTexture = TextureLoader.getTexture(imagePath("equipment/Accel.png"));
        berserkerTexture = TextureLoader.getTexture(imagePath("equipment/Berserker.png"));
        bladesTexture = TextureLoader.getTexture(imagePath("equipment/Blades.png"));
        blasterTexture = TextureLoader.getTexture(imagePath("equipment/Blaster.png"));
        firewallTexture = TextureLoader.getTexture(imagePath("equipment/Firewall.png"));
        hackTexture = TextureLoader.getTexture(imagePath("equipment/Hack.png"));
        metalTexture = TextureLoader.getTexture(imagePath("equipment/Metal.png"));
        overclockTexture = TextureLoader.getTexture(imagePath("equipment/Overclock.png"));
        shieldsTexture = TextureLoader.getTexture(imagePath("equipment/Shields.png"));
        tinkerTexture = TextureLoader.getTexture(imagePath("equipment/Tinker.png"));
        tutorial1 = TextureLoader.getTexture(imagePath("tutorial/Tutorial_image_1.png"));
        tutorial2 = TextureLoader.getTexture(imagePath("tutorial/Tutorial_image_2.png"));
        tutorial3 = TextureLoader.getTexture(imagePath("tutorial/Tutorial_image_3.png"));

        accelHB = new Hitbox(accelTexture.getWidth() * scale, accelTexture.getHeight() * scale);
        berserkerHB = new Hitbox(berserkerTexture.getWidth() * scale, berserkerTexture.getHeight() * scale);
        bladesHB = new Hitbox(bladesTexture.getWidth() * scale, bladesTexture.getHeight() * scale);
        blasterHB = new Hitbox(blasterTexture.getWidth() * scale, blasterTexture.getHeight() * scale);
        firewallHB = new Hitbox(firewallTexture.getWidth() * scale, firewallTexture.getHeight() * scale);
        hackHB = new Hitbox(hackTexture.getWidth() * scale, hackTexture.getHeight() * scale);
        metalHB = new Hitbox(metalTexture.getWidth() * scale, metalTexture.getHeight() * scale);
        overclockHB = new Hitbox(overclockTexture.getWidth() * scale, overclockTexture.getHeight() * scale);
        shieldsHB = new Hitbox(shieldsTexture.getWidth() * scale, shieldsTexture.getHeight() * scale);
        tinkerHB = new Hitbox(tinkerTexture.getWidth() * scale, tinkerTexture.getHeight() * scale);

        equipment.put("accel", new DisplayEquipment(AccelStance.STANCE_ID, accelTexture, smallCircleTexture, glitchedSmallCircleTexture, accelHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(AccelStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(AccelStance.STANCE_ID)).DESCRIPTION));
        equipment.put("berserker", new DisplayEquipment(BerserkerStance.STANCE_ID, berserkerTexture, smallCircleTexture,glitchedSmallCircleTexture, berserkerHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(BerserkerStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(BerserkerStance.STANCE_ID)).DESCRIPTION));
        equipment.put("blades", new DisplayEquipment(BladesStance.STANCE_ID, bladesTexture, circleTexture, glitchedCircleTexture, bladesHB,
                        LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(BladesStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(BladesStance.STANCE_ID)).DESCRIPTION));
        equipment.put("blaster", new DisplayEquipment(BlasterStance.STANCE_ID, blasterTexture, smallCircleTexture,glitchedSmallCircleTexture, blasterHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(BlasterStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(BlasterStance.STANCE_ID)).DESCRIPTION));
        equipment.put("firewall", new DisplayEquipment(ArtifactStance.STANCE_ID, firewallTexture, circleTexture,glitchedCircleTexture, firewallHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(ArtifactStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(ArtifactStance.STANCE_ID)).DESCRIPTION));
        equipment.put("hack",
                new DisplayEquipment(HackStance.STANCE_ID, hackTexture, smallCircleTexture,glitchedSmallCircleTexture, hackHB,
                        LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(HackStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(HackStance.STANCE_ID)).DESCRIPTION));
        equipment.put("metal", new DisplayEquipment(MetalStance.STANCE_ID, metalTexture, smallCircleTexture,glitchedSmallCircleTexture, metalHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(MetalStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(MetalStance.STANCE_ID)).DESCRIPTION));
        equipment.put("overclock", new DisplayEquipment(OverclockStance.STANCE_ID, overclockTexture, circleTexture,glitchedCircleTexture, overclockHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(OverclockStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(OverclockStance.STANCE_ID)).DESCRIPTION));
        equipment.put("shields", new DisplayEquipment(WallStance.STANCE_ID, shieldsTexture, circleTexture,glitchedCircleTexture, shieldsHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(WallStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(WallStance.STANCE_ID)).DESCRIPTION));
        equipment.put("tinker", new DisplayEquipment(TinkerStance.STANCE_ID, tinkerTexture, smallCircleTexture,glitchedSmallCircleTexture, tinkerHB,
                LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(TinkerStance.STANCE_ID)).NAME, LocalizedRunnerStanceStrings.getRunnerStanceStrings(makeID(TinkerStance.STANCE_ID)).DESCRIPTION));

        try
        {
            hasSeenEquipmentTutorialConfig = new SpireConfig("RunnerMod","RunnerModSave");
            hasSeenEquipmentTutorial = hasSeenEquipmentTutorialConfig.getBool("HasSeenEquipmentTutorial");
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static void renderEquipment(SpriteBatch sb) {
        if (!(AbstractDungeon.player instanceof RunnerCharacter)
                && !(AbstractDungeon.player.stance instanceof RunnerStance)) {
            return;
        }

        float cX = Settings.WIDTH / 8.0F;
        float cY = AbstractDungeon.player.hb.y + AbstractDungeon.player.hb.height + 200f * Settings.scale;

        float x1 = cX - 203.0F * scale;
        float x2 = cX - 105.0F * scale;
        // float x3 = cX;
        float x4 = cX + 105.0F * scale;
        float x5 = cX + 203.0F * scale;

        float y1 = cY + 215.0F * scale;
        float y2 = cY + 110.0F * scale;
        float y3 = cY + 56.0F * scale;
        // float y4 = cY;
        // float y5 = cY - 50.0F;
        float y6 = cY - 110.0F * scale;
        float y7 = cY - 215.0F * scale;

        equipment.get("blades").setPos(x1, y2);
        equipment.get("shields").setPos(x5, y2);
        equipment.get("firewall").setPos(cX, cY);
        equipment.get("overclock").setPos(cX, y7);

        equipment.get("accel").setPos(cX, y1);
        equipment.get("berserker").setPos(x1, y6);
        equipment.get("blaster").setPos(cX, y6);
        equipment.get("hack").setPos(x2, y3);
        equipment.get("metal").setPos(x4, y3);
        equipment.get("tinker").setPos(x5, y6);

        sb.setColor(alphaColor);

        Texture thisFrameCubeTexture;
        if(AbstractDungeon.player.stance instanceof GlitchedStance)
        {
            thisFrameCubeTexture = glitchedCubeTexture;
        }
        else
        {
            thisFrameCubeTexture = cubeTexture;
        }
        sb.draw(thisFrameCubeTexture, cX - (thisFrameCubeTexture.getWidth() * cubeScale) / 2F, cY - (thisFrameCubeTexture.getHeight()
                * cubeScale) / 2F, thisFrameCubeTexture.getWidth() * cubeScale, thisFrameCubeTexture.getHeight() * cubeScale);

        for (DisplayEquipment e : equipment.values()) {
            if (AbstractDungeon.player.stance instanceof RunnerStance
                    && !(AbstractDungeon.player.stance instanceof AKIRAStance)
                    && (e.durability > 0 || AbstractDungeon.player.stance.ID.equals(e.id))) {
                sb.setColor(Color.WHITE);
                renderEquipmentHelper(sb, e.texture, e.x, e.y, e.hb);

                if (e.id.equals(BladesStance.STANCE_ID) || e.id.equals(WallStance.STANCE_ID)
                        || e.id.equals(ArtifactStance.STANCE_ID) || e.id.equals(OverclockStance.STANCE_ID)) {
                    FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(
                                    e.durability), e.x + NUM_X_OFFSET, e.y + NUM_Y_OFFSET,
                            textColor, fontScale);
                }
            }else if (AbstractDungeon.player instanceof RunnerCharacter && AbstractDungeon.player.stance instanceof GlitchedStance)
            {
                sb.setColor(Color.WHITE);
                renderEquipmentHelper(sb, e.glitchedTexture, e.x, e.y, e.hb);
            } else {
                sb.setColor(alphaColor);
                renderEquipmentHelper(sb, e.inactiveTexture, e.x, e.y, e.hb);
            }
        }
    }

    private static void renderEquipmentHelper(SpriteBatch sb, Texture texture, float x, float y, Hitbox hb) {
        sb.draw(texture, x - (texture.getWidth() * scale) / 2F, y - (texture.getHeight()
                * scale) / 2F, texture.getWidth() * scale, texture.getHeight() * scale);

        // hb.resize(texture.getWidth() * scale, texture.getHeight() * scale);
        hb.move(x, y);
        hb.render(sb);
    }

    public static void updateEquipment() {
        if (!(AbstractDungeon.player instanceof RunnerCharacter)
                && !(AbstractDungeon.player.stance instanceof RunnerStance)) {
            return;
        }
        if(!equipmentRenderer.hasSeenEquipmentTutorial)
        {
            AbstractDungeon.ftue = new CustomMultiPageFtue(new Texture[]{tutorial1,tutorial2,tutorial3}, LocalizedRunnerTutorialStrings.getRunnerTutorialStrings(RunnerMod.makeID("EquipmentTutorial")).Lines);
            equipmentRenderer.hasSeenEquipmentTutorial = true;
            equipmentRenderer.hasSeenEquipmentTutorialConfig.setBool("HasSeenEquipmentTutorial", true);
            try
            {
                equipmentRenderer.hasSeenEquipmentTutorialConfig.save();
            }catch (Exception e)
            {
                System.out.println(e);
            }

        }

        for (DisplayEquipment e : equipment.values()) {
            e.hb.update();
            if (AbstractDungeon.player.stance instanceof RunnerStance) {
                Object durability = ((RunnerStance) AbstractDungeon.player.stance).durabilityDictionary.get(e.id);
                if (durability != null) {
                    e.durability = (int) durability;
                }
                else
                {
                    e.durability = 0;
                }

            }
            if (e.hb.hovered && !AbstractDungeon.isScreenUp && !CardCrawlGame.isPopupOpen) {
                TipHelper.renderGenericTip(e.hb.x + 96.0F * Settings.scale, e.hb.y + 64.0F * Settings.scale, e.name,
                        e.description);
            }
        }
    }

}
