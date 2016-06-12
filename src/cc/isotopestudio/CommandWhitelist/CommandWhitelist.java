package cc.isotopestudio.CommandWhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mars on 6/12/2016.
 * Copyright ISOTOPE Studio
 */
public class CommandWhitelist extends JavaPlugin implements Listener {

    private static final String pluginName = "CommandWhitelist";

    private static final Set<String> commandSet = new HashSet<>();

    @Override
    public void onEnable() {
        File file;
        file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    private void loadConfig() {
        commandSet.clear();
        reloadConfig();
        commandSet.addAll(getConfig().getStringList("whitelist"));
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().isOp())
            return;
        String cmd = event.getMessage();
        boolean allow = false;
        for (String tempCmd : commandSet) {
            int index = tempCmd.length() + 1;
            if (cmd.startsWith("/" + tempCmd)) {
                if (cmd.length() == index) {
                    allow = true;
                    break;
                }
                if (cmd.charAt(index) == ' ') {
                    allow = true;
                    break;
                }
            }
        }
        if (!allow) {
            event.setCancelled(true);
        }
    }

}
