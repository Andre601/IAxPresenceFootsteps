package ch.andre601.iaxpf.event;

import ch.andre601.iaxpf.IAxPresenceFootsteps;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.function.Function;

public class CommandEvents implements Listener{
    
    private final IAxPresenceFootsteps plugin;
    
    public CommandEvents(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPreprocess(PlayerCommandPreprocessEvent event){
        handle(event, event.isCancelled(), event.getPlayer(), PlayerCommandPreprocessEvent::getMessage);
    }
    
    @EventHandler
    public void onCommand(ServerCommandEvent event){
        handle(event, event.isCancelled(), event.getSender(), ServerCommandEvent::getCommand);
    }
    
    private <E> void handle(E event, boolean isCancelled, CommandSender sender, Function<E, String> toStringFunc){
        if(isCancelled)
            return;
        
        if(!plugin.getConfig().getBoolean("createOnIazip"))
            return;
        
        String command = toStringFunc.apply(event);
        if(command.isBlank())
            return;
        
        command = command.split("\\s+")[0];
        if(!command.equalsIgnoreCase("/iazip") && !command.equalsIgnoreCase("iazip"))
            return;
        
        sender.sendRichMessage("<grey>Creating blockmap.json file...");
        if(plugin.getBlockmapCreator().create(sender, false, true)){
            sender.sendRichMessage("<green>blockmap.json creation completed!");
        }else{
            sender.sendRichMessage("<gold>There were errors while creating the blockmap.json...");
        }
    }
}
