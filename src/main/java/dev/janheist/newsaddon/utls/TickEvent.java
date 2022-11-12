package dev.janheist.newsaddon.utls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class TickEvent {

    @SubscribeEvent
    public void onTick(net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent event) {
        if (Mouse.isButtonDown(0)) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
                ITextComponent itextcomponent = Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
                if (itextcomponent != null && itextcomponent.getStyle().getClickEvent() != null)
                    CustomClickEvent.execute(itextcomponent.getStyle().getClickEvent());
            }
        }
    }

}
