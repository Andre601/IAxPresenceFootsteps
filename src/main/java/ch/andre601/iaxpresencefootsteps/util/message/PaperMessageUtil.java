package ch.andre601.iaxpresencefootsteps.util.message;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class PaperMessageUtil implements MessageUtil{
    MiniMessage mm = MiniMessage.miniMessage();
    
    @Override
    public void sendMessage(CommandSender sender, String msg, Object... args){
        sender.sendMessage(mm.deserialize(String.format(msg, args)));
    }
}
