package ch.andre601.iaxpresencefootsteps.util;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.IAFolders;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class JsonCreator{
    
    private final FilenameFilter filter = (dir, name) -> name.endsWith(".yml");
    
    private final IAxPresenceFootsteps plugin;
    
    private final File blockmap;
    private final File cachedBlocks;
    
    public JsonCreator(IAxPresenceFootsteps plugin){
        String pluginDirectory = plugin.getDataFolder().getParent();
        
        this.plugin = plugin;
        this.blockmap = new File(pluginDirectory + IAFolders.PF_CONFIG, "blockmap.json");
        this.cachedBlocks = new File(pluginDirectory + IAFolders.IA_STORAGE, "real_blocks_note_ids_cache.yml");
    }
    
    public boolean createFile(CommandSender sender, boolean override){
        if(!cachedBlocks.exists()){
            plugin.getSenderFactory().sendMessage(sender, Messages.NO_BLOCK_CACHE);
            return false;
        }
    
        JSONObject json = new JSONObject();
    
        List<String> lines;
        try{
            lines = Files.readAllLines(cachedBlocks.toPath());
        }catch(IOException ex){
            plugin.getSenderFactory().sendMessage(sender, Messages.FILE_READ_ERROR, ex.getMessage());
            return false;
        }
        
        if(lines.isEmpty()){
            plugin.getSenderFactory().sendMessage(sender, Messages.FILE_EMPTY);
            return false;
        }
        
        if(blockmap.exists() && !override){
            plugin.getSenderFactory().sendMessage(sender, Messages.NO_OVERRIDE_ALLOWED_1);
            plugin.getSenderFactory().sendMessage(sender, Messages.NO_OVERRIDE_ALLOWED_2);
            return false;
        }
        
        getBlockmapValues(lines).forEach(json::put);
        
        try{
            if(!blockmap.createNewFile()){
                plugin.getSenderFactory().sendMessage(sender, Messages.BLOCKMAP_NOT_CREATED);
                return false;
            }
    
            InputStream is = new ByteArrayInputStream(json.toString().getBytes(StandardCharsets.UTF_8));
            
            Files.copy(is, blockmap.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            plugin.getSenderFactory().sendMessage(sender, Messages.BLOCKMAP_CREATED_1);
            plugin.getSenderFactory().sendMessage(sender, Messages.BLOCKMAP_CREATED_2);
    
            return true;
        }catch(IOException ex){
            plugin.getSenderFactory().sendMessage(sender, Messages.BLOCKMAP_CREATION_EXCEPTION, ex.getMessage());
            return false;
        }
    }
    
    private Map<String, String> getBlockmapValues(List<String> input){
        File folder;
        Map<String, String> blockmapValues = new HashMap<>();
        for(String value : input){
            String[] values = value.split(":");
            if(values.length < 3)
                continue;
            
            String namespace = values[0];
            String item = values[1];
            
            folder = new File(IAFolders.IA_ITEMS_PACKS + namespace);
            if(!folder.isDirectory())
                continue;
            
            File[] files = folder.listFiles(filter);
            if(files == null || files.length == 0)
                continue;
            
            Iterator<File> fileIterator = Arrays.stream(files).iterator();
            while(fileIterator.hasNext()){
                File file = fileIterator.next();
                if(file == null)
                    continue;
                
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                if(!config.contains("items." + item + ".specific_properties.block.pf_sound"))
                    continue;
                
                String sound = config.getString("items." + item + ".specific_properties.block.pf_sound", null);
                if(sound == null || sound.isEmpty())
                    continue;
                
                String block = getNoteBlockString(values[2]);
                if(block == null || block.isEmpty())
                    continue;
                
                blockmapValues.put(block, sound);
            }
        }
        
        return blockmapValues;
    }
    
    private String getNoteBlockString(String input){
        int value;
        try{
            value = Integer.parseInt(input);
        }catch(NumberFormatException ex){
            return null;
        }
    
        BlockData data = ItemsAdder.Advanced.getBlockDataByInternalId(value);
        if(data == null)
            return null;
        
        return data.getAsString();
    }
}
