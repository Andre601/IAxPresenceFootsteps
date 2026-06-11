package ch.andre601.iaxpf.util;

import ch.andre601.iaxpf.IAxPresenceFootsteps;
import com.google.gson.Gson;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class BlockmapCreator{
    
    private final Gson gson = new Gson();
    
    private final IAxPresenceFootsteps plugin;
    private final Path blockmapPath;
    
    public BlockmapCreator(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
        this.blockmapPath = plugin.getDataPath().getParent().resolve(
            "ItemsAdder/contents/presencefootsteps/" +
                "resourcepack/presencefootsteps/config/blockmap.json"
        );
    }
    
    public boolean create(CommandSender sender, boolean verbose, boolean automatic){
        if(plugin.getConfig().getBoolean("verboseEnabled"))
            verbose = true; // override verbose flag if set in config.
        
        Map<String, String> blockMap = new HashMap<>();
        for(String namespacedId : CustomBlock.getNamespacedIdsInRegistry()){
            CustomBlock block = CustomBlock.getInstance(namespacedId);
            if(block == null){
                if(verbose)
                    sender.sendRichMessage(String.format(
                        "<red>Skipping %s: Received null CustomBlock instance.",
                        namespacedId
                    ));
                
                continue;
            }
            
            BlockData data = block.getBaseBlockData();
            if(data == null){
                if(verbose)
                    sender.sendRichMessage(String.format(
                        "<red>Skipping %s: Received null BlockData.",
                        namespacedId
                    ));
                
                continue;
            }
            
            ConfigurationSection section;
            try{
                section = block.getConfigSectionCopy(true).getConfigurationSection("specific_properties.block");
            }catch(NoSuchMethodError | IllegalStateException ex){
                if(verbose)
                    sender.sendRichMessage("<red>Couldn't obtain Config Section. Attempting older approach...");
                
                section = block.getConfig().getConfigurationSection(
                    "items." + block.getId() + ".specific_properties.block"
                );
            }
            if(section == null){
                if(verbose)
                    sender.sendRichMessage(String.format(
                        "<red>Skipping %s: No specific_properties.block section found.",
                        namespacedId
                    ));
                
                continue;
            }
            
            String sound = section.getString("pf_sound");
            if(sound == null || sound.isEmpty()){
                if(verbose)
                    sender.sendRichMessage(String.format(
                        "<red>Skipping %s: No pf_sound option found or it was empty.",
                        namespacedId
                    ));
                
                continue;
            }
            
            if(verbose)
                sender.sendRichMessage(String.format(
                    "<green>Added sound '%s' for '%s' (%s) to blockmap.json!",
                    sound,
                    namespacedId,
                    data.getAsString()
                ));
            
            blockMap.put(data.getAsString(), sound);
        }
        
        if(blockMap.isEmpty()){
            sender.sendRichMessage("<grey>blockmap was empty. Skipping file creation...");
            return true;
        }
        
        sender.sendRichMessage(String.format(
            "<grey>Collected %d %s. Generating blockmap.json file...",
            blockMap.size(),
            blockMap.size() == 1 ? "entry" : "entries"
        ));
        
        if(!Files.exists(blockmapPath) && !blockmapPath.toFile().getParentFile().mkdirs()){
            sender.sendRichMessage("<red>Couldn't create folders for blockmap.json!");
            return false;
        }
        
        String json = gson.toJson(blockMap);
        if(json == null || json.isEmpty()){
            sender.sendRichMessage("<red>Couldn't create JSON for blockmap.json file!");
            return false;
        }
        
        try(InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))){
            Files.copy(is, blockmapPath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            return false;
        }
        
        sender.sendRichMessage("<green>Successfully created blockmap.json file!");
        
        if(!automatic)
            sender.sendRichMessage("<green>Run <grey>/iazip</grey> to apply it to the resource pack!");
        
        return true;
    }
}
