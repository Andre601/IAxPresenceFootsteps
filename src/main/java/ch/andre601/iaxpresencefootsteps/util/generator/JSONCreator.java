package ch.andre601.iaxpresencefootsteps.util.generator;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;
import ch.andre601.iaxpresencefootsteps.util.constants.Messages;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public interface JSONCreator{
    
    File resolveConfigsFolder(String namespace);
    
    IAxPresenceFootsteps getPlugin();
    
    File getBlockmapFile();
    
    File getBlockCacheFile();
    
    default void createFile(CommandSender sender, boolean override){
        if(!getBlockCacheFile().exists()){
            getPlugin().getMessageUtil().sendMessage(sender, Messages.NO_BLOCK_CACHE);
            return;
        }
        
        JSONObject json = new JSONObject();
        List<String> lines;
        
        try{
            lines = Files.readAllLines(getBlockCacheFile().toPath());
        }catch(IOException ex){
            getPlugin().getMessageUtil().sendMessage(sender, Messages.FILE_READ_ERROR, ex.getMessage());
            return;
        }
        
        if(lines.isEmpty()){
            getPlugin().getMessageUtil().sendMessage(sender, Messages.FILE_EMPTY);
            return;
        }
        
        if(getBlockmapFile().exists() && !override){
            getPlugin().getMessageUtil().sendMessage(sender, Messages.NO_OVERRIDE_ALLOWED_1);
            getPlugin().getMessageUtil().sendMessage(sender, Messages.NO_OVERRIDE_ALLOWED_2);
            return;
        }
        
        getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCK_COLLECTION_START);
        resolveBlockmapValues(sender, lines).forEach(json::put);
        getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCK_COLLECTION_END);
        
        try{
            if(getBlockmapFile().getParentFile().mkdirs() && getBlockmapFile().createNewFile())
                getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCKMAP_FILE_CREATED);
            
            InputStream is = new ByteArrayInputStream(json.toString().getBytes(StandardCharsets.UTF_8));
            Files.copy(is, getBlockmapFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCKMAP_CREATED);
        }catch(IOException ex){
            getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCKMAP_CREATION_EXCEPTION, ex.getMessage());
            return;
        }
        
        if(getPlugin().getConfig().getBoolean("auto-zip")){
            getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCKMAP_IAZIP_TRIGGER);
            Bukkit.dispatchCommand(sender, "iazip");
            return;
        }
        
        getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCKMAP_IAZIP_REMIND);
    }
    
    default Map<String, String> resolveBlockmapValues(CommandSender sender, List<String> input){
        Map<String, String> blockmapValues = new HashMap<>();
        for(String value : input){
            String[] values = value.split(":");
            if(values.length < 3)
                continue;
            
            String namespace = values[0];
            String item = values[1];
            
            File folder = resolveConfigsFolder(namespace);
            if(folder == null || !folder.exists() || !folder.isDirectory())
                continue;
            
            File[] files = folder.listFiles(getFilter());
            if(files == null || files.length == 0)
                continue;
            
            Arrays.stream(files).filter(Objects::nonNull).forEach(file -> {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                if(!config.contains("items." + item + ".specific_properties.block.pf_sound"))
                    return;
                
                String sound = config.getString("items." + item + ".specific_properties.block.pf_sound", null);
                if(sound == null || sound.isEmpty())
                    return;
                
                String block = resolveNoteBlockValues(values[2]);
                if(block == null || block.isEmpty())
                    return;
                
                getPlugin().getMessageUtil().sendMessage(sender, Messages.BLOCK_FOUND, namespace, item);
                blockmapValues.put(block, sound);
            });
        }
        
        return blockmapValues;
    }
    
    default String resolveNoteBlockValues(String input){
        int value;
        try{
            value = Integer.parseInt(input.trim());
        }catch(NumberFormatException ex){
            return null;
        }
        
        @SuppressWarnings("deprecation")
        BlockData blockData = ItemsAdder.Advanced.getBlockDataByInternalId(value);
        if(blockData == null)
            return null;
        
        return blockData.getAsString();
    }
    
    default FilenameFilter getFilter(){
        return (dir, name) -> name.endsWith(".yml");
    }
}
