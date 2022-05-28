package ch.andre601.iaxpresencefootsteps;

import ch.andre601.iaxpresencefootsteps.commands.PFCreate;
import ch.andre601.iaxpresencefootsteps.commands.PFReload;
import ch.andre601.iaxpresencefootsteps.util.JsonCreator;

import ch.andre601.iaxpresencefootsteps.util.message.MessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.PaperMessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.SpigotMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class IAxPresenceFootsteps extends JavaPlugin{
    private MessageUtil messageUtil;
    private JsonCreator jsonCreator;
    
    private ConsoleCommandSender console;
    
    @Override
    public void onEnable(){
        saveDefaultConfig();
        this.console = Bukkit.getConsoleSender();
        
        try{
            Class.forName("io.papermc.paper.text.PaperComponents");
    
            messageUtil = new PaperMessageUtil(this);
            getMessageUtil().sendMessage("<grey>Found PaperMC. Using native message system from it...");
        }catch(ClassNotFoundException ex){
            messageUtil = new SpigotMessageUtils(this);
            getMessageUtil().sendMessage("<grey>Found SpigotMC. Using BukkitAudiences from Kyori Adventure...");
        }
        
        PluginManager manager = getServer().getPluginManager();
        if(!manager.isPluginEnabled("ItemsAdder")){
            getMessageUtil().sendMessage("<red>ItemsAdder was not found! IAxPresenceFootsteps requires this plugin to work.");
            manager.disablePlugin(this);
            return;
        }
        
        jsonCreator = new JsonCreator(this);
        
        if(!loadCommands()){
            manager.disablePlugin(this);
            return;
        }
        
        getMessageUtil().sendMessage("<green>IAxPresenceFootsteps ready!");
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
