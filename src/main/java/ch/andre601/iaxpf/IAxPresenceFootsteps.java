package ch.andre601.iaxpf;

import ch.andre601.iaxpf.command.CmdIAxPF;
import ch.andre601.iaxpf.event.CommandEvents;
import ch.andre601.iaxpf.util.BlockmapCreator;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.paper.PaperCommandManager;

public class IAxPresenceFootsteps extends JavaPlugin{
    
    private final BlockmapCreator blockmapCreator = new BlockmapCreator(this);
    
    @Override
    public void onEnable(){
        if(!Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")){
            getSLF4JLogger().warn("==========================");
            getSLF4JLogger().warn(" ItemsAdder not found!");
            getSLF4JLogger().warn(" This plugin is required!");
            getSLF4JLogger().warn("");
            getSLF4JLogger().warn(" Disabling plugin...");
            getSLF4JLogger().warn("==========================");
            
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        
        saveDefaultConfig();
        
        getServer().getPluginManager().registerEvents(new CommandEvents(this), this);
        
        PaperCommandManager<CommandSourceStack> manager = PaperCommandManager.builder()
            .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
            .buildOnEnable(this);
        manager.parameterInjectorRegistry().registerInjector(IAxPresenceFootsteps.class, ParameterInjector.constantInjector(this));
        
        AnnotationParser<CommandSourceStack> parser = new AnnotationParser<>(manager, CommandSourceStack.class);
        parser.parse(new CmdIAxPF());
    }
    
    public BlockmapCreator getBlockmapCreator(){
        return blockmapCreator;
    }
}
