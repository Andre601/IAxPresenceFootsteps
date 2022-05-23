package ch.andre601.iaxpresencefootsteps.util.message;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class SpigotSenderFactory implements SenderFactory{
    
    MiniMessage mm = MiniMessage.miniMessage();
    BukkitAudiences adventure;
    
    public SpigotSenderFactory(IAxPresenceFootsteps plugin){
        adventure = BukkitAudiences.create(plugin);
    }
    
    @Override
    public void sendMessage(CommandSender sender, String message, Object... args){
        adventure.sender(sender).sendMessage(mm.deserialize(String.format(message, args)));
    }
}
