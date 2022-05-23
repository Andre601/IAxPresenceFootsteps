package ch.andre601.iaxpresencefootsteps.commands;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PFCreate implements CommandExecutor{
    
    private final IAxPresenceFootsteps plugin;
    
    public PFCreate(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
        if(!sender.hasPermission("iaxpf.command.pfcreate")){
            plugin.getMessageUtil().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }
        
        boolean override = args.length > 0 && Boolean.parseBoolean(args[0]);
        plugin.getJsonCreator().createFile(sender, override);
        
        return true;
    }
}
