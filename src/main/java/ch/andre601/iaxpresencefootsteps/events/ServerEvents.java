package ch.andre601.iaxpresencefootsteps.events;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerEvents implements Listener{
    
    private final IAxPresenceFootsteps plugin;
    
    public ServerEvents(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    // Workaround for wanky softdepend stuff: https://github.com/PaperMC/Paper/issues/5992
    @EventHandler
    public void onServerLoad(ServerLoadEvent event){
        plugin.startPlugin();
    }
}
