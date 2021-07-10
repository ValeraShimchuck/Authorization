package com.auth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class Auth implements CommandExecutor {
    private Main plugin;
    public Auth(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        String name = sender.getName();
        if(cmd.getName().equals("reg")){
            if(args.length != 2) return false;
            if(!args[0].equals(args[1])) return  false;
            if(plugin.data.getBoolean("players."+name+"."+"log",false)){
                sender.sendMessage(plugin.getConfig().getString("messages.alrlog"));
                return true;
            }
            if(plugin.data.getBoolean("players."+name+"."+"reg",false)){
                sender.sendMessage(plugin.getConfig().getString("messages.alrreg"));
                return true;
            }
            plugin.data.set("players."+name+"."+"reg",true);
            plugin.data.set("players."+name+"."+"log",true);
            plugin.data.set("players."+name+"."+"pass",args[0]);
            try {
                plugin.data.save(plugin.datafile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            sender.sendMessage(plugin.getConfig().getString("messages.sucreg"));
            plugin.updateHM();
            return true;
        }
        if(args.length != 1) return false;
        if(plugin.data.getBoolean("players."+name+"."+"log",false)){
            sender.sendMessage(plugin.getConfig().getString("messages.alrlog"));
            return true;}
        if(plugin.data.getString("players."+name+"."+"pass").equals(args[0])){
            plugin.data.set("players."+name+"."+"log",true);
            try {
                plugin.data.save(plugin.datafile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            sender.sendMessage(plugin.getConfig().getString("messages.suclog"));
            plugin.updateHM();
            return true;
        }
        sender.sendMessage(plugin.getConfig().getString("messages.wrongpass"));
        plugin.logPlayersAtt.replace(name,plugin.logPlayersAtt.get(name)+1);
        return true;
    }
}
