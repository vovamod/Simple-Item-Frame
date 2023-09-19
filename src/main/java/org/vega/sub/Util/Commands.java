package org.vega.sub.Util;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import static org.vega.sub.SimpleItemFrame.*;

public class Commands implements CommandExecutor{
    public Commands() {
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("sifreload")) {
            if (sender instanceof ConsoleCommandSender || sender.hasPermission("sif.manage")|| sender.isOp()) {
                try {
                    INSTANCE.reloadConfig();
                    INSTANCE.loadConfig();
                    sender.sendMessage(prefix + " " + m_reload);
                } catch (Exception ignored) {}
            } else {
                sender.sendMessage(prefix + " " + m_permission);
            }
            return true;
        } if (command.getName().equalsIgnoreCase("sif")) {
            if (sender instanceof ConsoleCommandSender || sender.hasPermission("sif.manage")|| sender.isOp()) {
                try {
                    sender.sendMessage(prefix + " Version: " + version);
                } catch (Exception ignored) {}
            } else {
                sender.sendMessage(prefix + " " + m_permission);
            }
            return true;
        }
        return false;
    }
}
