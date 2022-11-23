package ch.andre601.iaxpresencefootsteps.util.generator;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;

import java.io.File;

public class NewIAJSONCreator implements JSONCreator{
    
    private final IAxPresenceFootsteps plugin;
    
    private final File contents;
    private final File blockmap;
    private final File cachedBlocks;
    
    public NewIAJSONCreator(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
        
        String pluginsDirectory = plugin.getDataFolder().getParent();
        
        this.contents = new File(pluginsDirectory + "/ItemsAdder/contents/");
        this.blockmap = new File(pluginsDirectory + "ItemsAdder/contents/presencefootsteps/resourcepack/presencefootsteps/config/blockmap.json");
        this.cachedBlocks = new File(pluginsDirectory + "/ItemsAdder/storage/", "real_blocks_note_ids_cache.yml");
    }
    
    @Override
    public File resolveConfigsFolder(String namespace){
        return new File(this.contents, namespace + "/configs/");
    }
    
    @Override
    public IAxPresenceFootsteps getPlugin(){
        return plugin;
    }
    
    @Override
    public File getBlockmapFile(){
        return blockmap;
    }
    
    @Override
    public File getBlockCacheFile(){
        return cachedBlocks;
    }
}
