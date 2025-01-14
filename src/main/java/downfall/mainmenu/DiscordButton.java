package downfall.mainmenu;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import downfall.downfallMod;

import java.awt.*;
import java.net.URL;

public class DiscordButton {
    static public Button discordButton = new Button(1400.0F * Settings.xScale, Settings.HEIGHT / 2 - 200f * Settings.yScale, "", ImageMaster.loadImage("downfallResources/images/discord.png"));

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "update"
    )
    public static class Update {
        public static void Postfix(MainMenuScreen __instance) {
            if (__instance.screen == MainMenuScreen.CurScreen.MAIN_MENU && downfallMod.STEAM_MODE) {
                discordButton.update();

                float a = ReflectionHacks.getPrivate(__instance.bg, TitleBackground.class, "logoAlpha");

                discordButton.alpha = a;

                discordButton.DRAW_Y = Settings.HEIGHT / 2 - 200f * Settings.yScale - 70.0F * Settings.scale * __instance.bg.slider;

                if (discordButton.hb.clicked == true) {
                    openWebpage("https://discord.gg/GtDrBX2vpr");
                    discordButton.hb.clicked = false;
                }
            }

        }
    }

    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "render"
    )
    public static class Render {
        public static void Postfix(MainMenuScreen __instance, SpriteBatch sb) {
            if (__instance.screen == MainMenuScreen.CurScreen.MAIN_MENU && downfallMod.STEAM_MODE) {
                discordButton.render(sb);
            }

        }
    }
}