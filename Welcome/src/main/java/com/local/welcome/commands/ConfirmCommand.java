package com.local.welcome.commands;

import com.local.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfirmCommand implements CommandExecutor {
    private final Welcome plugin;

    public ConfirmCommand(Welcome plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if(sender instanceof Player){
            if(args.length == 0){
                player.sendMessage(player + " 소지 금액: " + plugin.getEconomy().getBalance(player));
            }
        }
        return false;
    }
}
