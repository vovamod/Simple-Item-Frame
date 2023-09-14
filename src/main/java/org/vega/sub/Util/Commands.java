package org.vega.sub.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.vega.sub.SimpleItemFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.vega.sub.SimpleItemFrame.*;

public class Commands implements CommandExecutor, TabCompleter {
    private final SimpleItemFrame plugin;

    public Commands(SimpleItemFrame plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("sif") && args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender instanceof ConsoleCommandSender || !sender.hasPermission(OP_PERMISSION)) {
                try{
                    this.plugin.reloadConfig();
                    OP_PERMISSION = new Permission("simpleitemframe.reload");
                    VISIBILITY_PERMISSION = new Permission("simpleitemframe.visibility");
                    OP_PERMISSION.setDefault(PermissionDefault.valueOf(Objects.requireNonNull(this.plugin.getConfig().getString("permission_manager")).toUpperCase()));
                    VISIBILITY_PERMISSION.setDefault(PermissionDefault.valueOf(Objects.requireNonNull(this.plugin.getConfig().getString("permission_visibility")).toUpperCase()));

                    reloadItemMaterial();
                } catch (Exception e) {
                    this.plugin.getLogger().severe("SIF will be disabled, config damaged\nReport of stacktrace:" + e);
                    sender.sendMessage(prefix + " " + m_error.replace("(e)", (CharSequence) e));
                    this.plugin.getServer().getPluginManager().disablePlugin(plugin);
                }

                if (VISIBILITY_PERMISSION == null) {
                    Bukkit.getLogger().warning("Invalid Permission specified for visibility. Setting automatically to false");
                    VISIBILITY_PERMISSION.setDefault(PermissionDefault.FALSE);
                }
                if (OP_PERMISSION ==null){
                    Bukkit.getLogger().warning("Invalid Permission specified for managing SIF. Setting automatically to true");
                    VISIBILITY_PERMISSION.setDefault(PermissionDefault.TRUE);
                }
                sender.sendMessage(prefix + " " + m_reload);
            } else {
                sender.sendMessage(prefix + " " + m_permission);
            }
            return true;
        }
        return false;
    }
    public void reloadItemMaterial() {
        String itemTypeName = Objects.requireNonNull(this.plugin.getConfig().getString("item")).toUpperCase();
        if (Material.getMaterial(itemTypeName) == null) {
            Bukkit.getLogger().warning("Invalid item type specified in the configuration: " + itemTypeName + "\nSetting SHEARS as a default item");
            Logic.setItemMaterial(Material.SHEARS);
        }
        Logic.setItemMaterial(Material.getMaterial(itemTypeName));
    }
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("reload");
        }
        return completions;
    }
}
