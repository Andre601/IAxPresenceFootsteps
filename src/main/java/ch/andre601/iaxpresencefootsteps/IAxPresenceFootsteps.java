package ch.andre601.iaxpresencefootsteps;

import ch.andre601.iaxpresencefootsteps.commands.PFCreate;
import ch.andre601.iaxpresencefootsteps.util.JsonCreator;

import ch.andre601.iaxpresencefootsteps.util.message.MessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.PaperMessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.SpigotMessageUtils;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class IAxPresenceFootsteps extends JavaPlugin{
    private MessageUtil messageUtil;
    private JsonCreator jsonCreator;
    
    @Override
    public void onEnable(){
        PluginManager manager = getServer().getPluginManager();
        if(!manager.isPluginEnabled("ItemsAdder")){
            getLogger().warning("ItemsAdder is not enabled! IAxPresenceFootsteps requires it to work.");
            manager.disablePlugin(this);
            return;
        }
        
        getLogger().info("Initializing MessageUtil...");
        try{
            Class.forName("io.papermc.paper.text.PaperComponents");
            
            getLogger().info("Found PaperMC. Using native Message System...");
            messageUtil = new PaperMessageUtil();
        }catch(ClassNotFoundException ex){
            getLogger().info("Found SpigotMC. Using Message System from Kyori-Adventure...");
            messageUtil = new SpigotMessageUtils(this);
        }
        
        jsonCreator = new JsonCreator(this);
        
        getLogger().info("Registering /pfcreate command...");
        PluginCommand pfCreateCommand = this.getCommand("pfcreate");
        if(pfCreateCommand == null){
            getLogger().warning("Could not register /pfcreate command. Disabling plugin...");
            manager.disablePlugin(this);
            return;
        }
        
        pfCreateCommand.setExecutor(new PFCreate(this));
        getLogger().info("Command successfully registered!");
        
        getLogger().info("IAxPresenceFootsteps is ready!");
    }
    
    public MessageUtil getMessageUtil(){
        return messageUtil;
    }
    
    public JsonCreator getJsonCreator(){
        return jsonCreator;
    }
}
