package com.auth;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Main extends JavaPlugin implements Runnable {
    public File datafile;
    public FileConfiguration data;
    public HashMap<String, Boolean> loggedPlayers;
    public HashMap<String, Integer> logPlayersTime;
    public HashMap<String, Integer> logPlayersAtt;
    @Override
    public void onEnable() {
        File config = new File(getDataFolder(),"config.yml");
        if(!config.exists()){
            getLogger().info("Creating cfg file...");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        getLogger().info("Enable Authorization");
        getDataFolder().mkdirs();
        datafile = new File(getDataFolder(), "data.yml");
        if(!datafile.exists()){
            try {
                datafile.createNewFile();
                data = YamlConfiguration.loadConfiguration(datafile);
                data.createSection("players");
                data.save(datafile);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        data = YamlConfiguration.loadConfiguration(datafile);
        Bukkit.getPluginManager().registerEvents(new Handler(this),this);
        getCommand("login").setExecutor(new Auth(this));
        getCommand("reg").setExecutor(new Auth(this));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,this,0,20);
        loggedPlayers = new HashMap<>();
        logPlayersTime = new HashMap<>();
        logPlayersAtt = new HashMap<>();
        Set<String> players = data.getConfigurationSection("players").getKeys(false);
        for(String name : players){
            loggedPlayers.put(name, data.getBoolean("players."+name+".log",false));
        }
        getLogger().info(String.valueOf(loggedPlayers));




    }
    public void updateHM(){
        Set<String> players = data.getConfigurationSection("players").getKeys(false);
        for(String names : players){
            loggedPlayers.put(names, data.getBoolean("players."+names+".log",false));
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disable Authorization");
    }

    @Override
    public void run() {
        Set<String> names = loggedPlayers.keySet();
        for(String name: names){
            Player p = Bukkit.getServer().getPlayer(name);
            if (p != null)if(!loggedPlayers.get(name)){
                if(!data.getBoolean("players."+p.getName()+"."+"log",false)) {
                    if(logPlayersTime.containsKey(name)){
                       logPlayersTime.replace(name,logPlayersTime.get(name)+1);
                       if(logPlayersTime.get(name) >= this.getConfig().getInt("integers.logtime",11)){
                           p.kickPlayer(this.getConfig().getString("messages.longkick"));
                       }
                       if(logPlayersAtt.get(name) >= this.getConfig().getInt("integers.attempts",3)){
                           p.kickPlayer(this.getConfig().getString("messages.attkick"));
                       }
                    }
                    //p.sendMessage("players."+p.getName()+"."+"log");
                    if(!data.getBoolean("players."+p.getName()+"."+"reg",false)){
                        p.sendMessage(this.getConfig().getString("messages.regmess"));
                    }else p.sendMessage(this.getConfig().getString("messages.logmess"));

                }
            }
        }
    }
}
