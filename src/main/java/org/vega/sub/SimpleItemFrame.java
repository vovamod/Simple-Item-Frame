package org.vega.sub;

import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleItemFrame extends JavaPlugin implements Listener {

    public SimpleItemFrame() {
    }
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onInteraction(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            Player player = event.getPlayer();
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand.getType() == Material.SHEARS && event.getHand() == EquipmentSlot.HAND && player.isSneaking()) {
                event.setCancelled(true);
                toggleItemFrameVisibility(itemFrame);
                player.getInventory().setItemInMainHand(itemInHand);
            }
        }
    }
    private void toggleItemFrameVisibility(ItemFrame itemFrame) {
        if (itemFrame.isVisible()) {
            itemFrame.setVisible(false);
        } else {
            itemFrame.setVisible(true);
        }
    }
}
