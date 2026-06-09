package ch.andre601.iaxpf.command;

import ch.andre601.iaxpf.IAxPresenceFootsteps;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;

@Command("iaxpf")
@Permission("iaxpf.admin")
public class CmdIAxPF{
    
    @Command("create")
    @Permission({"iaxpf.admin", "iaxpf.command.create"})
    public void create(CommandSourceStack source, IAxPresenceFootsteps plugin, @Flag("verbose") boolean verbose){
        CommandSender sender = source.getSender();
        sender.sendRichMessage("<grey>Creating blockmap.json file...");
        
        if(plugin.getBlockmapCreator().create(sender, verbose)){
            sender.sendRichMessage("<green>blockmap.json creation completed!");
        }else{
            sender.sendRichMessage("<gold>There were errors while creating the blockmap.json...");
        }
    }
}
