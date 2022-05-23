# IAxPresenceFootsteps

This is a simple plugin that adds support for the Presence Footsteps mod to ItemsAdder.

## The Goal

The goal is to add a way to automatically generate the `blockmap.json` file used by Presence Footsteps to override sounds for custom blocks.

## How to

> **Note**  
> Make sure to have used `/iazip` AT LEAST once before using this plugin, as it requires a valid `real_blocks_note_ids_cache.yml` to be present.

In order to use this plugin, open any YAML file in ItemsAdder that contains items with the `block` property set.

In the file, add the following (Example using `/ItemsAdder/data/items_packs/itemsadder/blocks.yml`):  
```diff
items:
  ruby_block:
    display_name: display-name-ruby_block
    permission: ruby_block
    resource:
      material: PAPER
      generate: true
      textures:
      - block/ruby_block.png
  specific_properties
    block:
      placed_model:
        type: REAL_NOTE
        break_particles_material: REDSTONE_BLOCK
      break_tools_whitelist:
      - pickaxe
      - PICKAXE
+     pf_sound: 'stone'
```

After that, save the file(s) and run `/pfcreate` to create the `blockmap.json` file.  

> **warning**  
> If such a file already exists will you need to add `true` as an argument to the command to override it!
