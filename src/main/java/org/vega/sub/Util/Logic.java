package org.vega.sub.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.vega.sub.SimpleItemFrame;

import java.util.Objects;

import static org.vega.sub.SimpleItemFrame.*;

public class Logic implements Listener {
    private static Material itemMaterial;
    public Logic(SimpleItemFrame plugin) {
        FileConfiguration config = plugin.getConfig();
        String itemTypeName = Objects.requireNonNull(config.getString("item")).toUpperCase();
        itemMaterial = Material.getMaterial(itemTypeName);
        if (itemMaterial == null) {
            Bukkit.getLogger().warning("Invalid item type specified in the configuration: " + itemTypeName + "\nSetting SHEARS as a default item");
            itemMaterial = Material.SHEARS;
        }
    }
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, SimpleItemFrame.getPlugin(SimpleItemFrame.class));
    }

    @EventHandler
    public void onInteraction(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof org.bukkit.entity.ItemFrame) {
            Player player = event.getPlayer();
            org.bukkit.entity.ItemFrame itemFrame = (org.bukkit.entity.ItemFrame) event.getRightClicked();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemMaterial != null && itemInHand.getType() == itemMaterial && event.getHand() == EquipmentSlot.HAND && player.isSneaking() ) {
                if (!player.hasPermission(VISIBILITY_PERMISSION)) {
                    event.setCancelled(true);
                    toggleItemFrameVisibility(itemFrame);
                    player.getInventory().setItemInMainHand(itemInHand);
                } else {
                    player.sendMessage(prefix + " " + m_permission);
                }
            }
        }
    }
    private void toggleItemFrameVisibility(org.bukkit.entity.ItemFrame itemFrame) {
        itemFrame.setVisible(!itemFrame.isVisible());
    }
    public static void setItemMaterial(Material material) {
        itemMaterial = material;
    }
}
