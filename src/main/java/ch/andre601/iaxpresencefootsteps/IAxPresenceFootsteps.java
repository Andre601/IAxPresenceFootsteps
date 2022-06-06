package ch.andre601.iaxpresencefootsteps;

import ch.andre601.iaxpresencefootsteps.commands.PFCreate;
import ch.andre601.iaxpresencefootsteps.commands.PFReload;
import ch.andre601.iaxpresencefootsteps.events.ServerEvents;
import ch.andre601.iaxpresencefootsteps.util.JsonCreator;

import ch.andre601.iaxpresencefootsteps.util.message.MessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.PaperMessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.SpigotMessageUtils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class IAxPresenceFootsteps extends JavaPlugin{
    private MessageUtil messageUtil;
    private JsonCreator jsonCreator;
    private ConsoleCommandSender console;
    
    @Override
    public void onLoad(){
        saveDefaultConfig();
        
        console = getServer().getConsoleSender();
        
        jsonCreator = new JsonCreator(this);
        
        loadMessageUtil();
    }
    
    @Override
    public void onEnable(){
        getMessageUtil().sendMessage("<grey>Preparing IAxPresenceFootsteps...");
        try{
            Class.forName("org.bukkit.event.server.ServerLoadEvent");
        
            new ServerEvents(this);
        }catch(ClassNotFoundException ex){
            // I doubt people use IA on a server that isn't the latest 3, but you never know...
            getServer().getScheduler().runTaskLater(this, this::startPlugin, 1L);
        }
    
        getMessageUtil().sendMessage("<grey>Completed preparation. Waiting for Server to finish...");
    }
    
    public MessageUtil getMessageUtil(){
        return messageUtil;
    }
    
    public JsonCreator getJsonCreator(){
        return jsonCreator;
    }
    
    public ConsoleCommandSender getConsole(){
        return console;
    }
    
    public void startPlugin(){
        PluginManager manager = getServer().getPluginManager();
        
        getMessageUtil().sendMessage("<grey>Server marked as 'done'! Checking for ItemsAdder...");
        if(!manager.isPluginEnabled("ItemsAdder")){
            getMessageUtil().sendMessage("<red>ItemsAdder is not enabled! IAxPresenceFootsteps requires it to work.");
            manager.disablePlugin(this);
            return;
        }
        
        if(loadCommands()){
            getMessageUtil().sendMessage("<green>Successfully enabled IAxPresenceFootsteps!");
        }else{
            manager.disablePlugin(this);
        }
    }
    
    private void loadMessageUtil(){
        try{
            Class.forName("io.papermc.paper.text.PaperComponents");
        
            messageUtil = new PaperMessageUtil(this);
            getMessageUtil().sendMessage("<grey>Found PaperMC. Using native message system from it...");
        }catch(ClassNotFoundException ex){
            messageUtil = new SpigotMessageUtils(this);
            getMessageUtil().sendMessage("<grey>Found SpigotMC. Using BukkitAudiences from Kyori Adventure...");
        }
    }
    
    private boolean loadCommands(){
        getMessageUtil().sendMessage("<grey>Loading <aqua>/pfcreate</aqua> command...");
        PluginCommand pfCreate = this.getCommand("pfcreate");
        if(pfCreate == null){
            getMessageUtil().sendMessage("<red>Unable to load <grey>/pfcreate</grey> command!");
            return false;
        }
        
        PFCreate pfCreateCmd = new PFCreate(this);
        pfCreate.setExecutor(pfCreateCmd);
        pfCreate.setTabCompleter(pfCreateCmd);
        getMessageUtil().sendMessage("<grey>Successfully loaded <aqua>/pfcreate</aqua>!");
    
        getMessageUtil().sendMessage("<grey>Loading <aqua>/pfreload</aqua> command...");
        PluginCommand pfReloead = this.getCommand("pfreload");
        if(pfReloead == null){
            getMessageUtil().sendMessage("<red>Unable to load <grey>/pfreload</grey> command!");
            return false;
        }
        
        pfReloead.setExecutor(new PFReload(this));
        getMessageUtil().sendMessage("<grey>Successfully loaded <aqua>/pfreload</aqua>!");
        
        return true;
    }
}
