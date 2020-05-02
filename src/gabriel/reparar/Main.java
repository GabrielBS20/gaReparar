package gabriel.reparar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("§a[gaReparar] §f Plugin ativado com sucesso!");
        Bukkit.getConsoleSender().sendMessage("§aMeu discord: Gabriel Santos#5773");

        Bukkit.getPluginManager().registerEvents(new Reparar(), this);
        getCommand("reparar").setExecutor(new Reparar());

    }
}
