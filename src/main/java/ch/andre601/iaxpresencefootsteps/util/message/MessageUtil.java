package ch.andre601.iaxpresencefootsteps.util.message;

import org.bukkit.command.CommandSender;

public interface MessageUtil{
    
    void sendMessage(CommandSender sender, String msg, Object... args);
    
    void sendMessage(String msg, Object... args);
}
