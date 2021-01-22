package com.local.welcome.events;

import com.local.welcome.Welcome;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Events implements Listener {
    private final Welcome plugin;

    public Events(Welcome plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void ePlayerLogin(PlayerLoginEvent e){
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()){
            for(Player all_players : Bukkit.getOnlinePlayers()){
                all_players.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("접두사") + plugin.gold + player + plugin.white + "님이 서버에 처음 접속 했습니다."));
                all_players.sendMessage("지금부터 " + plugin.getConfig().getInt("지연 초") + "초 동안 " + player + "님에게 인사하면 " + plugin.getConfig().getInt("지급 금액") + "원을 획득합니다!");
                plugin.welcome_list.add(all_players);
                plugin.impossible_chat.add(player);
                List<String> s = plugin.getConfig().getStringList("인사 메시지");
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<s.size(); i++){
                    sb.append(s.get(i)).append(", ");
                }
                sb.deleteCharAt(sb.lastIndexOf(", "));
                all_players.sendMessage("ex) " + sb.toString());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        plugin.impossible_chat.remove(player);
                        for(Player all_players : Bukkit.getOnlinePlayers())
                            plugin.welcome_list.remove(all_players);
                    }
                }.runTaskLater(plugin, plugin.getConfig().getInt("지연 초") * 20);
            }
        }
    }

    @EventHandler
    public void ePlayerChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if(plugin.welcome_list.contains(player)) {
            for (String s : plugin.getConfig().getStringList("인사 메시지")) {
                if (!plugin.receive_list.contains(player)) {
                    if (e.getMessage().equals(s)) {
                        plugin.getEconomy().depositPlayer(player, plugin.getConfig().getInt("지급 금액"));
                        player.sendMessage("신입유저에게 인사를 해서 " + plugin.getConfig().getInt("지급 금액") + "원을 획득 하셨습니다.");
                        plugin.receive_list.add(player);
                    }
                } else if (plugin.receive_list.contains(player))
                    player.sendMessage("당신은 이미 지급 받았습니다.");
            }
        }else if(plugin.impossible_chat.contains(player)){
            e.setCancelled(true);
            player.sendMessage(plugin.red + "처음 서버에 접속후 5초간 채팅을 칠 수 없습니다!");
        }
    }
}
