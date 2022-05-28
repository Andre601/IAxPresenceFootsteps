package ch.andre601.iaxpresencefootsteps.util.message;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class PaperMessageUtil implements MessageUtil{
    private final MiniMessage mm = MiniMessage.miniMessage();
    private final IAxPresenceFootsteps plugin;
    
    public PaperMessageUtil(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
    }
    
    @Override
    public void sendMessage(CommandSender sender, String msg, Object... args){
        sender.sendMessage(mm.deserialize(String.format(Messages.PREFIX + msg, args)));
    }
    
    @Override
    public void sendMessage(String msg, Object... args){
        sendMessage(plugin.getConsole(), msg, args);
    }
}
