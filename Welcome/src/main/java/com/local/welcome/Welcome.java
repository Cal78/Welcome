package com.local.welcome;

import com.local.welcome.commands.ConfirmCommand;
import com.local.welcome.events.Events;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Welcome extends JavaPlugin {
    public static Economy economy;
    public ArrayList<Player> welcome_list = new ArrayList<>();
    public ArrayList<Player> receive_list = new ArrayList<>();
    public ArrayList<Player> impossible_chat = new ArrayList<>();

    public String red = ChatColor.RED + "";
    public String gold = ChatColor.GOLD + "";
    public String white = ChatColor.WHITE + "";

    @Override
    public void onEnable() {
        getCommand("player_money").setExecutor(new ConfirmCommand(this));
        getServer().getPluginManager().registerEvents(new Events(this), this);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public Economy getEconomy(){
        return economy;
    }
}
