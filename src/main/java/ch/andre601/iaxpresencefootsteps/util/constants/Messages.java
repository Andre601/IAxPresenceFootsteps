package ch.andre601.iaxpresencefootsteps.util.constants;

public class Messages{
    
    public static final String OUTDATED_ITEMSADDER = "<red>You are using an outdated version of ItemsAdder! Use at least 3.1.5.";
    
    public static final String NO_BLOCK_CACHE = "<red>ItemsAdder does not have a <grey>real_blocks_note_ids_cache.yml</grey> file!";
    public static final String FILE_READ_ERROR = "<red>There was an error while reading the block cache file from ItemsAdder! Error: %s";
    public static final String FILE_EMPTY = "<red>The <grey>real_blocks_note_ids_cache.yml</grey> file does not have any entries.";
    
    public static final String NO_OVERRIDE_ALLOWED_1 = "<red>A blockmap.json file already exists! Not overriding file...";
    public static final String NO_OVERRIDE_ALLOWED_2 = "<red>Use <grey>/pfcreate true</grey> to force an override.";
    
    public static final String BLOCK_COLLECTION_START = "<grey>Collecting valid custom blocks. Check console for progress...";
    public static final String BLOCK_COLLECTION_END = "<green>Collecting of custom blocks complete! Creating <grey>blockmap.json</grey>...";
    
    public static final String BLOCKMAP_FILE_CREATED = "<green>No <grey>blockmap.json</grey> found. Creating it...";
    public static final String BLOCKMAP_CREATION_EXCEPTION = "<red>Could not create the blockmap.json due to an IOException: %s";
    public static final String BLOCKMAP_CREATED_1 = "<green>Successfully created <grey>blockmap.json</grey> file.";
    public static final String BLOCKMAP_CREATED_2 = "<green>Remember to use <grey>/iazip</grey> to update the resource pack.";
    
    public static final String NO_PERMISSION = "<red>You lack the permission <grey>iaxpf.create</grey> to use this command!";
}
