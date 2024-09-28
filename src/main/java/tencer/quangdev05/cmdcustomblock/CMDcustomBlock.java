package tencer.quangdev05.cmdcustomblock;

import tencer.quangdev05.cmdcustomblock.listeners.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class CMDcustomBlock extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Kiểm tra và tạo file config.yml nếu nó không tồn tại
        createConfig();

        // Đăng ký sự kiện
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getLogger().info("CMDcustomBlock đã được bật!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CMDcustomBlock đã bị tắt!");
    }

    // Lệnh /cmdcb reload
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cmdcb")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                config = getConfig();
                sender.sendMessage("Plugin đã được tải lại!");
                return true;
            }
        }
        return false;
    }

    public FileConfiguration getPluginConfig() {
        return this.config;
    }

    private void createConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getLogger().info("File Config không tồn tại, đang tạo lại file mặc định config.yml...");
            saveDefaultConfig();
        }
        config = getConfig();
    }
}