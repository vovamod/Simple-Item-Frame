package org.vega.sub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.vega.sub.Util.Commands;
import org.vega.sub.Util.Logic;
import java.util.Objects;

public final class SimpleItemFrame extends JavaPlugin implements Listener {
    public static Permission VISIBILITY_PERMISSION;
    public static Permission OP_PERMISSION;
    private Commands commands;
    public static String prefix;
    public static String m_permission;
    public static String m_error;
    public static String m_reload;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("prefix")));
        m_permission = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("m_permission")));
        m_error = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("m_error")));
        m_reload = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("m_reload")));

        try {
            OP_PERMISSION = new Permission("simpleitemframe.reload");
            VISIBILITY_PERMISSION = new Permission("simpleitemframe.visibility");
            OP_PERMISSION.setDefault(PermissionDefault.valueOf(Objects.requireNonNull(config.getString("permission_manager")).toUpperCase()));
            VISIBILITY_PERMISSION.setDefault(PermissionDefault.valueOf(Objects.requireNonNull(config.getString("permission_visibility")).toUpperCase()));
        } catch (Exception e) {
            getLogger().severe(m_error.replace("(e)", (CharSequence) e));
            getServer().getPluginManager().disablePlugin(this);
        }
        if (VISIBILITY_PERMISSION == null) {
            Bukkit.getLogger().warning("Invalid Permission specified for visibility. Setting automatically to false");
            VISIBILITY_PERMISSION.setDefault(PermissionDefault.FALSE);
        }
        if (OP_PERMISSION ==null){
            Bukkit.getLogger().warning("Invalid Permission specified for managing SIF. Setting automatically to true");
            VISIBILITY_PERMISSION.setDefault(PermissionDefault.TRUE);
        }
        getServer().getPluginManager().registerEvents(this, this);
        Logic logic = new Logic(this);
        commands = new Commands(this);
        logic.registerEvents();
        Objects.requireNonNull(getCommand("sif")).setTabCompleter(commands);
    }

    @Override
    public void onDisable() {
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        return commands.onCommand(sender, command, label, args);
    }
}
