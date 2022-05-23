package ch.andre601.iaxpresencefootsteps.commands;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PFCreate extends Command{
    
    private final IAxPresenceFootsteps plugin;
    
    protected PFCreate(IAxPresenceFootsteps plugin, @NotNull String name, @NotNull String description,
                       @NotNull String usageMessage, @NotNull List<String> aliases){
        super(name, description, usageMessage, aliases);
        this.plugin = plugin;
    }
    
    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings){
        if(!commandSender.hasPermission("iaxpf.command.create")){
            plugin.getSenderFactory().sendMessage(commandSender, Messages.NO_PERMISSION);
            return true;
        }
        
        boolean override = false;
        if(strings.length >= 1)
            override = Boolean.getBoolean(strings[0]);
        
        plugin.getJsonCreator().createFile(commandSender, override);
        return true;
    }
}
