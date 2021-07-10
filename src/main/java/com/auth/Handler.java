package com.auth;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;

import java.io.IOException;

public class Handler implements Listener {
    private final Main plugin;

    public Handler(Main plugin){ this.plugin = plugin;}

    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String name = p.getName();

        if(!plugin.data.isSet("players."+name)){
            plugin.data.getConfigurationSection("players").createSection(name);
            plugin.data.set("players."+name+"."+"log",false);
            plugin.data.set("players."+name+"."+"reg",false);
            try {
                plugin.data.save(plugin.datafile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else{
            plugin.data.set("players."+name+"."+"log",false);
            try {
                plugin.data.save(plugin.datafile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(plugin.data.getBoolean("Player."+p.getName()+"."+"reg",false)){
            p.sendMessage(plugin.getConfig().getString("messages.regmess"));
        }else p.sendMessage(plugin.getConfig().getString("messages.logmess"));
        //Set<String> players = plugin.data.getConfigurationSection("players").getKeys(false);
        //for(String names : players){
        //plugin.getLogger().info("Pass");

        //plugin.getLogger().info(String.valueOf(plugin.logPlayersTime));

        //}

            //plugin.getLogger().info("Passif1");
            if(!plugin.logPlayersTime.containsKey(name)){
                //plugin.getLogger().info("Passif2");
                plugin.loggedPlayers.put(name, plugin.data.getBoolean("players."+name+".log",false));
                plugin.logPlayersTime.put(name, 0);
                plugin.logPlayersAtt.put(name,0);
            }else{
                //plugin.getLogger().info("Passelse2");
                plugin.loggedPlayers.replace(name, plugin.data.getBoolean("players."+name+".log",false));
                plugin.logPlayersTime.replace(name,0);
                plugin.logPlayersAtt.replace(name,0);
            }


    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        //p.sendMessage("you are move!");
            if(!plugin.data.getBoolean("players."+p.getName()+"."+"log",false)) {
                //p.sendMessage("players."+p.getName()+"."+"log");
                e.setCancelled(true);
                /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
                //    p.sendMessage("/reg password confirmPassword");
                }else p.sendMessage("/login password");*/

            }



    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        //p.sendMessage("you are move!");
        if(!plugin.data.getBoolean("players."+p.getName()+"."+"log",false)) {
            //p.sendMessage("players."+p.getName()+"."+"log");
            e.setCancelled(true);
            /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
            p.sendMessage("/reg password confirmPassword");
            }else p.sendMessage("/login password");*/

        }
    }
    @EventHandler
    public void  onUse(PlayerInteractEvent e){
        Player p = e.getPlayer();
        //p.sendMessage("you are move!");
        if(!plugin.data.getBoolean("players."+p.getName()+"."+"log",false)) {
            //p.sendMessage("players."+p.getName()+"."+"log");
            e.setCancelled(true);
            /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
            p.sendMessage("/reg password confirmPassword");
            }else p.sendMessage("/login password");*/
    }
}
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        //p.sendMessage("you are move!");
        if(!plugin.data.getBoolean("players."+p.getName()+"."+"log",false)) {
            //p.sendMessage("players."+p.getName()+"."+"log");
            e.setCancelled(true);
            /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
            p.sendMessage("/reg password confirmPassword");
            }else p.sendMessage("/login password");*/
        }
}
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e)
    {
        Player p = e.getPlayer();
        p.sendMessage(e.getMessage());
        if(!plugin.data.getBoolean("players."+p.getName()+"."+"log",false)) {
            //p.sendMessage("players."+p.getName()+"."+"log");
            if(e.getMessage().length() >= 4){ if(!e.getMessage().startsWith("/login") && !e.getMessage().startsWith("/reg")){
                p.sendMessage(String.valueOf(e.getMessage().startsWith("/login")));
                p.sendMessage(String.valueOf(e.getMessage().startsWith("/reg")));
                e.setCancelled(true);
                /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
            p.sendMessage("/reg password confirmPassword");
            }else p.sendMessage("/login password");*/
            }
        }else{
                e.setCancelled(true);
                /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
            p.sendMessage("/reg password confirmPassword");
            }else p.sendMessage("/login password");*/
            }

        }
    }
    @EventHandler
    public void onOpenInventory(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        //p.sendMessage("you are move!");
        if(!plugin.data.getBoolean("players."+p.getName()+"."+"log",false)) {
            //p.sendMessage("players."+p.getName()+"."+"log");
            e.setCancelled(true);
            /*if(!plugin.data.getBoolean("players."+p.getName()+"."+"reg",false)){
            p.sendMessage("/reg password confirmPassword");
            }else p.sendMessage("/login password");*/

        }

    }
}
