package org.vega.sub.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.vega.sub.SimpleItemFrame;
import static org.vega.sub.SimpleItemFrame.*;

public class Logic implements Listener {
    public Logic() {
    }
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, SimpleItemFrame.getPlugin(SimpleItemFrame.class));
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteraction(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof org.bukkit.entity.ItemFrame) {
            Player player = event.getPlayer();
            org.bukkit.entity.ItemFrame itemFrame = (org.bukkit.entity.ItemFrame) event.getRightClicked();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemMaterial != null && itemInHand.getType() == itemMaterial && event.getHand() == EquipmentSlot.HAND && player.isSneaking()) {
                if (player.hasPermission("sif.visibility")) {
                    event.setCancelled(true);
                    toggleItemFrameVisibility(itemFrame);
                } else {
                    player.sendMessage(prefix + " " + m_permission);
                }
            }
        }
    }
    private void toggleItemFrameVisibility(org.bukkit.entity.ItemFrame itemFrame) {
        itemFrame.setVisible(!itemFrame.isVisible());
    }
}
