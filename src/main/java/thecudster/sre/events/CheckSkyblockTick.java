package thecudster.sre.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.SmartFontRenderer;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

/* Modified from Wynntils under GNU Affero General Public License v3.0
* https://github.com/Wynntils/Wynntils/blob/development/LICENSE
* @author Wynntils
*/
public class CheckSkyblockTick {
    public static int ticks = 0;
    public static ScaledResolution screen = null;
    public static SmartFontRenderer fontRenderer = null;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        refresh();
        if (Utils.inSkyblock) {
            CurrentLoc.checkLoc();
        }
        if (ticks % 20 == 0) {
            if (Minecraft.getMinecraft().thePlayer != null) {
                Utils.checkForSkyblock();
                Utils.checkForDungeons();
            }
            ticks = 0;
        }

        ticks++;
    }
    @SubscribeEvent
    public static void onLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        SkyblockReinvented.discordRPC.stop();
    }
    @SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        SkyblockReinvented.discordRPC.start();
    }
    public static void refresh() {
        Minecraft mc = Minecraft.getMinecraft();
        screen = new ScaledResolution(mc);
        if (fontRenderer == null) {
            try {
                fontRenderer = new SmartFontRenderer();
            } catch (Throwable ignored) {
            } finally {
                if (fontRenderer != null) {
                    if (mc.gameSettings.language != null) {
                        fontRenderer.setUnicodeFlag(mc.isUnicode());
                        fontRenderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
                    }
                    ((IReloadableResourceManager)mc.getResourceManager()).registerReloadListener(fontRenderer);
                }
            }
        }
    }

}
