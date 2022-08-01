package dev.janheist.newsaddon.timer;

import dev.janheist.newsaddon.main.NewsAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SpeedWarning extends TimerTask {
    NewsAddon newsAddon;
    public List<BlockPos> speedCams = new ArrayList<>();
    boolean first = true;

    public SpeedWarning(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    public void initialize() {
        first = false;
        speedCams.add(new BlockPos(-273, 80, 338));
    }

    @Override
    public void run() {
        if (first) initialize();

        if (!newsAddon.speedcam) return;

        EntityPlayerSP p = Minecraft.getMinecraft().player;
        try {
            if (!p.isRiding() && !(p.getRidingEntity() instanceof EntityArmorStand))
                return;

            EntityArmorStand e = (EntityArmorStand) p.getRidingEntity();

            if (e == null || !e.hasCustomName() || !e.getCustomNameTag().contains("vehicleseatstand."))
                return;

            BlockPos loc = p.getPosition();

            BlockPos nearest = null;
            double dist = Double.MAX_VALUE;
            for (BlockPos cam : speedCams) {
                double d = cam.getDistance(loc.getX(), loc.getY(), loc.getZ());
                if (d < dist) {
                    nearest = cam;
                    dist = d;
                }
            }

            if (nearest != null && dist < 275) {
                double d = nearest.getDistance(loc.getX(), loc.getY(), loc.getZ());
                String distStr = String.format("%.1f", d);
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§c§lBlitzer in " + distStr + "m", true);
            }

        } catch (NullPointerException ignored) { }

    }
}
