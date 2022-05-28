package ch.andre601.iaxpresencefootsteps.util.message;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class SpigotMessageUtils implements MessageUtil{
    
    private final IAxPresenceFootsteps plugin;
    private final BukkitAudiences adventure;
    private final MiniMessage mm = MiniMessage.miniMessage();
    
    public SpigotMessageUtils(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
        this.adventure = BukkitAudiences.create(plugin);
    }
    
    @Override
    public void sendMessage(CommandSender sender, String msg, Object... args){
        adventure.sender(sender).sendMessage(mm.deserialize(String.format(Messages.PREFIX + msg, args)));
    }
    
    @Override
    public void sendMessage(String msg, Object... args){
        sendMessage(plugin.getConsole(), msg, args);
    }
}
