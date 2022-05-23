package ch.andre601.iaxpresencefootsteps.util.message;

import org.bukkit.command.CommandSender;

public interface SenderFactory{
    void sendMessage(CommandSender sender, String message, Object... args);
}
