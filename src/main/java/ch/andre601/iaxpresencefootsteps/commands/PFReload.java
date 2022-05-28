package ch.andre601.iaxpresencefootsteps.commands;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PFReload implements CommandExecutor{
    
    private final IAxPresenceFootsteps plugin;
    
    public PFReload(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
        if(!sender.hasPermission("iaxpf.command.pfreload")){
            plugin.getMessageUtil().sendMessage(sender, Messages.NO_PERMISSION, "iaxpf.command.pfreload");
            return true;
        }
        
        plugin.getMessageUtil().sendMessage(sender, Messages.CONFIG_RELOADING);
        plugin.reloadConfig();
        plugin.getMessageUtil().sendMessage(sender, Messages.CONFIG_RELOAD_SUCCESS);
        
        return true;
    }
}
