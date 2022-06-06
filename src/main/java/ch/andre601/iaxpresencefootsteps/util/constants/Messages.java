package ch.andre601.iaxpresencefootsteps.util.constants;

public class Messages{
    
    public static final String PREFIX = "<white>[<aqua>IAxPresenceFootsteps</aqua>]</white> ";
    
    // JsonCreator.createFiles messages
    public static final String OUTDATED_ITEMSADDER = "<red>You are using an outdated version of ItemsAdder! Use at least 3.1.5.";
    public static final String NO_BLOCK_CACHE = "<red>ItemsAdder does not have a <grey>real_blocks_note_ids_cache.yml</grey> file!";
    public static final String FILE_READ_ERROR = "<red>There was an error while reading the block cache file from ItemsAdder! Error: %s";
    public static final String FILE_EMPTY = "<red>The <grey>real_blocks_note_ids_cache.yml</grey> file does not have any entries.";
    
    public static final String NO_OVERRIDE_ALLOWED_1 = "<red>A blockmap.json file already exists! Not overriding file...";
    public static final String NO_OVERRIDE_ALLOWED_2 = 
        "<red>Use <grey><click:run_command:/pfcreate --override><hover:show_text:'<grey>Click to execute command'>" + 
        "/pfcreate --override</hover></click></grey> to force an override.";
    
    public static final String BLOCK_COLLECTION_START = "<grey>Collecting valid custom blocks. Check console for progress...";
    public static final String BLOCK_COLLECTION_END = "<green>Collecting of custom blocks complete! Creating <grey>blockmap.json</grey>...";
    
    public static final String BLOCKMAP_FILE_CREATED = "<green>No <grey>blockmap.json</grey> found. Creating new one...";
    public static final String BLOCKMAP_CREATED = "<green>Successfully created <grey>blockmap.json</grey> file.";
    public static final String BLOCKMAP_IAZIP_TRIGGER = "<grey>Executing <aqua>/iazip</aqua>...";
    public static final String BLOCKMAP_IAZIP_REMIND =
        "<green>Remember to use <grey><click:run_command:/iazip><hover:show_text:'<grey>Click to execute command'>" +
        "/iazip</hover></click></grey> to update the resource pack.";
    
    public static final String BLOCKMAP_CREATION_EXCEPTION = "<red>Could not create the blockmap.json due to an IOException: %s";
    
    // JsonCreator.getBlockmapValues message
    public static final String BLOCK_FOUND = "<grey>Found <aqua>%s:%s</aqua> with pf_sound configuration. Adding to blockmap.json file...";
    
    // Command messages
    public static final String NO_PERMISSION = "<red>You lack the permission <grey>%s</grey> to use this command!";
    
    public static final String CONFIG_RELOADING = "<grey>Reloading <aqua>config.yml</aqua>...";
    public static final String CONFIG_RELOAD_SUCCESS = "<green>Config.yml successfully reloaded!";
}
