package org.vega.sub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.vega.sub.Util.Commands;
import org.vega.sub.Util.Logic;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
public final class SimpleItemFrame extends JavaPlugin implements Listener {
    // Commands + Logic vars
    private Commands commands;
    // Variables from config which will be used later (VOVA IMPLEMENT THE GODDAMN CHECK CLASS SYSTEM)
    public static String itemTypeName;
    public static String prefix;
    public static String version;
    public static String m_permission;
    public static String m_error;
    public static String m_reload;
    public static Material itemMaterial;
    //Local vars within classes
    public static SimpleItemFrame INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        // Easy config reload + check
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(INSTANCE, INSTANCE);
        // Other and Main logic
        commands = new Commands();
        Logic logic = new Logic();
        logic.registerEvents();
    }
    // Pass command handle event to Commands logic Class
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        return commands.onCommand(sender, command, label, args);
    }
    // Fast and easy logic to reload config in any case
    public void loadConfig() {
        //Making config var for easy use
        FileConfiguration config = getConfig();
        //Strings load
        try {
            // Strings load
            version = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("version")));
            prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("prefix")));
            m_permission = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("m_permission")));
            m_error = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("m_error")));
            m_reload = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("m_reload")));
            itemTypeName = Objects.requireNonNull(config.getString("item")).toUpperCase();
        } catch (Exception e) {
            // IF SMT BROKEN REPORT HERE
            getLogger().severe("SIF will be restored to default settings, config damaged\nReport of stacktrace:\n" + e);
            errorConfig();
        }
        itemMaterial = Material.getMaterial(itemTypeName);
        if (Material.getMaterial(itemTypeName) == null) {
            Bukkit.getLogger().warning("Invalid item type specified in the configuration: " + itemTypeName + ". Setting SHEARS as a default item");
            itemMaterial = Material.SHEARS;
        } else {
            itemMaterial = Material.getMaterial(itemTypeName);
        }
    }
    public void errorConfig(){
        FileConfiguration config = getConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File brokenConfigFile = new File(getDataFolder(), timestamp + "-broken-config.yml");
        configFile.renameTo(brokenConfigFile);
        // Load default config
        saveDefaultConfig();
        reloadConfig();
        loadConfig();
        version = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("version")));
        prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("prefix")));
        m_permission = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("m_permission")));
        m_error = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("m_error")));
        m_reload = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("m_reload")));
        itemTypeName = Objects.requireNonNull(config.getString("item")).toUpperCase();
        itemMaterial = Material.getMaterial(itemTypeName);
    }
}
