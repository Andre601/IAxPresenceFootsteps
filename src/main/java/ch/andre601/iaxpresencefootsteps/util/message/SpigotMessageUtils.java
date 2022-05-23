package ch.andre601.iaxpresencefootsteps.util.message;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class SpigotMessageUtils implements MessageUtil{
    
    private final BukkitAudiences adventure;
    private final MiniMessage mm = MiniMessage.miniMessage();
    
    public SpigotMessageUtils(IAxPresenceFootsteps plugin){
        this.adventure = BukkitAudiences.create(plugin);
    }
    
    @Override
    public void sendMessage(CommandSender sender, String msg, Object... args){
        adventure.sender(sender).sendMessage(mm.deserialize(String.format(msg, args)));
    }
}
