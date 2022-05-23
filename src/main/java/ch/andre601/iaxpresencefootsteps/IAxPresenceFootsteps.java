package ch.andre601.iaxpresencefootsteps;

import ch.andre601.iaxpresencefootsteps.util.JsonCreator;
import ch.andre601.iaxpresencefootsteps.util.message.PaperSenderFactory;
import ch.andre601.iaxpresencefootsteps.util.message.SenderFactory;
import ch.andre601.iaxpresencefootsteps.util.message.SpigotSenderFactory;
import org.bukkit.plugin.java.JavaPlugin;

public class IAxPresenceFootsteps extends JavaPlugin{
    
    private SenderFactory senderFactory;
    private JsonCreator jsonCreator;
    
    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        
        try{
            Class.forName("io.papermc.paper.text.PaperComponents");
            getLogger().info("Found PaperComponents. Using Paper's native Component system...");
            
            this.senderFactory = new PaperSenderFactory();
        }catch(ClassNotFoundException ex){
            getLogger().info("Enabling SpigotSenderFactory for MiniMessage compatability...");
            
            this.senderFactory = new SpigotSenderFactory(this);
        }
        
        jsonCreator = new JsonCreator(this);
        
        this.getCommand("pfcreate");
    }
    
    public SenderFactory getSenderFactory(){
        return senderFactory;
    }
    
    public JsonCreator getJsonCreator(){
        return jsonCreator;
    }
}
