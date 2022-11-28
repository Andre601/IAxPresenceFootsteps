package ch.andre601.iaxpresencefootsteps.util.generator;

import ch.andre601.iaxpresencefootsteps.IAxPresenceFootsteps;

import java.io.File;

public class LegacyIAJSONCreator implements JSONCreator{
    
    private final IAxPresenceFootsteps plugin;
    
    private final File itemsPacks;
    private final File blockmap;
    private final File cachedBlocks;
    
    public LegacyIAJSONCreator(IAxPresenceFootsteps plugin){
        this.plugin = plugin;
        
        String pluginsDirectory = plugin.getDataFolder().getParent();
        
        this.itemsPacks = new File(pluginsDirectory + "/ItemsAdder/data/items_packs/");
        this.blockmap = new File(pluginsDirectory + "/ItemsAdder/data/resource_pack/assets/presencefootsteps/config/", "blockmap.json");
        this.cachedBlocks = new File(pluginsDirectory + "/ItemsAdder/storage/", "real_blocks_note_ids_cache.yml");
    }
    
    @Override
    public File resolveConfigsFolder(String namespace){
        return new File(this.itemsPacks, namespace + "/");
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
