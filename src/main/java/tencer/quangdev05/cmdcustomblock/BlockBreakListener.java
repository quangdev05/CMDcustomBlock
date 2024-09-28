package tencer.quangdev05.cmdcustomblock.listeners;

import tencer.quangdev05.cmdcustomblock.CMDcustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class BlockBreakListener implements Listener {

    private final CMDcustomBlock plugin;

    public BlockBreakListener(CMDcustomBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        String worldName = block.getWorld().getName();
        Material blockType = block.getType();

        FileConfiguration config = plugin.getPluginConfig();
        if (config.contains("blocks")) {
            for (String key : config.getConfigurationSection("blocks").getKeys(false)) {
                String blockName = config.getString("blocks." + key + ".block");
                List<String> worldBlackList = config.getStringList("blocks." + key + ".WorldBlackList");
                String command = config.getString("blocks." + key + ".command");

                if (blockType.toString().equalsIgnoreCase(blockName) && !worldBlackList.contains(worldName)) {
                    // Chạy lệnh console khi block bị phá
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", event.getPlayer().getName()));
                }
            }
        }
    }
}
