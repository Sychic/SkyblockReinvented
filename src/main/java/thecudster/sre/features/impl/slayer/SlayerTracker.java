package thecudster.sre.features.impl.slayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Util;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.*;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.Utils;

public class SlayerTracker {
    public static String rngesusMeter = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String xpLeft = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String display = EnumChatFormatting.GREEN + "XP Until Next Level: " + xpLeft + "\nRNGesus Meter " + rngesusMeter;
    public static String displayXP = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";;
    public static String rngesus = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";;
    public static String current = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    private String meterText = "RNGesus Meter: -------------------- ";
    private int nxtLvl = " - Next LVL in ".length();
    private String xpLeftRev = "   Zombie Slayer LVL ";
    private String xpLeftTara = "   Spider Slayer LVL ";
    private String xpLeftSven = "   Wolf Slayer LVL ";
    private static final Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        display = EnumChatFormatting.GREEN + "XP Until Next Level: " + xpLeft + "\n" + EnumChatFormatting.GREEN + "RNGesus Meter: " + rngesusMeter + "\n"
                + EnumChatFormatting.GREEN + "Current Slayer: " + currentSlayer;
        displayXP = EnumChatFormatting.GREEN + "XP Until Next Level: " + xpLeft;
        current = EnumChatFormatting.GREEN + "Current Slayer: " + currentSlayer;
        rngesus = EnumChatFormatting.GREEN + "RNGesus Meter: " + rngesusMeter;

    }
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (SkyblockReinvented.config.slayerInfo) {
            String unformatted = event.message.getUnformattedText();
            if (unformatted.contains(meterText)) {
                unformatted = EnumChatFormatting.LIGHT_PURPLE + unformatted.substring(meterText.length() + 3);
                SlayerTracker.rngesusMeter = unformatted;
                event.setCanceled(true);
                return;
            }
            if (unformatted.contains(xpLeftRev)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Revenant";
                unformatted = unformatted.substring(xpLeftRev.length() + nxtLvl + 1, unformatted.length() - 1);
                SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                event.setCanceled(true);
                return;
            }
            if (unformatted.contains(xpLeftTara)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Tarantula";
                unformatted = unformatted.substring(xpLeftTara.length() + nxtLvl + 1, unformatted.length() - 1);
                SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                event.setCanceled(true);
                return;
            }
            if (unformatted.contains(xpLeftSven)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Sven";
                unformatted = unformatted.substring(xpLeftSven.length() + nxtLvl + 1, unformatted.length() - 1);
                SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                event.setCanceled(true);
                return;
            }
        }
    }
    static {
        new SlayerXPGuiElement();
        new SlayerCurrentGuiElement();
        new SlayerRNGGuiElement();
    }
    public static class SlayerXPGuiElement extends GuiElement {
        public SlayerXPGuiElement() {
            super("Slayer XP", new FloatPair(0.00625f, 0.14626351f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                ScreenRenderer.fontRenderer.drawString(displayXP, leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY(), CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScreenRenderer.fontRenderer.drawString(displayXP, 0.00625f, 0.14626351f, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.slayerInfo;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(displayXP);
        }
    }
    public static class SlayerRNGGuiElement extends GuiElement {
        public SlayerRNGGuiElement() {
            super("Slayer RNG", new FloatPair(0.005208337f, 0.18264504f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                ScreenRenderer.fontRenderer.drawString(rngesus, leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY(), CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScreenRenderer.fontRenderer.drawString(rngesus, 0.005208337f, 0.18264504f, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.slayerInfo;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(rngesus);
        }
    }
    public static class SlayerCurrentGuiElement extends GuiElement {
        public SlayerCurrentGuiElement() {
            super("Slayer Current", new FloatPair(0.004687488f, 0.106932156f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                ScreenRenderer.fontRenderer.drawString(current, leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY(), CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScreenRenderer.fontRenderer.drawString(current, 0.004687488f, 0.106932156f, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.slayerInfo;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(current);
        }
    }
}
