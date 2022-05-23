package ch.andre601.iaxpresencefootsteps.util.message;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class PaperSenderFactory implements SenderFactory{
    
    private final MiniMessage mm = MiniMessage.miniMessage();
    
    @Override
    public void sendMessage(CommandSender sender, String message, Object... args){
        sender.sendMessage(mm.deserialize(String.format(message, args)));
    }
}
