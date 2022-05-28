package ch.andre601.iaxpresencefootsteps.commands;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class PFCreate implements CommandExecutor, TabExecutor{
    
    private final IAxPresenceFootsteps plugin;
    
    public PFCreate(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
        if(!sender.hasPermission("iaxpf.command.pfcreate")){
            plugin.getMessageUtil().sendMessage(sender, Messages.NO_PERMISSION, "iaxpf.command.pfcreate");
            return true;
        }
        
        boolean override = args.length > 0 && args[0].equalsIgnoreCase("--override");
        plugin.getJsonCreator().createFile(sender, override);
        
        return true;
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
        if(args.length != 1)
            return null;
        
        String overrideArgs = "--override";
        
        if(overrideArgs.length() >= args[0].length() && overrideArgs.regionMatches(true, 0, args[0], 0, args[0].length()))
            return Collections.singletonList(overrideArgs);
        
        return null;
    }
}
