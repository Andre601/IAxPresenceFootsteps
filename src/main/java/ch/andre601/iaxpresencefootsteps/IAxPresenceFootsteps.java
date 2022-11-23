package ch.andre601.iaxpresencefootsteps;

import ch.andre601.iaxpresencefootsteps.commands.PFCreate;
import ch.andre601.iaxpresencefootsteps.commands.PFReload;
import ch.andre601.iaxpresencefootsteps.events.ServerEvents;
import ch.andre601.iaxpresencefootsteps.util.JsonCreator;

import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import ch.andre601.iaxpresencefootsteps.util.generator.JSONCreator;
import ch.andre601.iaxpresencefootsteps.util.generator.NewIAJSONCreator;
import ch.andre601.iaxpresencefootsteps.util.generator.OldIAJSONCreator;
import ch.andre601.iaxpresencefootsteps.util.message.MessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.PaperMessageUtil;
import ch.andre601.iaxpresencefootsteps.util.message.SpigotMessageUtils;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class IAxPresenceFootsteps extends JavaPlugin{
    private MessageUtil messageUtil;
    private JSONCreator jsonCreator;
    private ConsoleCommandSender console;
    
    @Override
    public void onLoad(){
        saveDefaultConfig();
        
        console = getServer().getConsoleSender();
    }
    
    @Override
    public void onEnable(){
        loadMessageUtil();
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
    
    public JSONCreator getJsonCreator(){
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
        
        try{
            ItemsAdder.Advanced.class.getMethod("getBlockDataByInternalId", int.class);
        }catch(NoSuchMethodException ex){
            getMessageUtil().sendMessage(Messages.OUTDATED_ITEMSADDER);
            manager.disablePlugin(this);
            return;
        }
    
        Path iaFolder = getDataFolder().getParentFile().toPath().resolve("ItemsAdder");
        if(iaFolder.resolve("contens").toFile().exists() && iaFolder.resolve("contens").toFile().isDirectory()){
            jsonCreator = new NewIAJSONCreator(this);
        }else
        if(iaFolder.resolve("data").toFile().exists() && iaFolder.resolve("data").toFile().isDirectory()){
            jsonCreator = new OldIAJSONCreator(this);
        }else{
            getMessageUtil().sendMessage("<red>Couldn't resolve ItemsAdder add-on folder structure!");
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
