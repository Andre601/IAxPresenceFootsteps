# IAxPresenceFootsteps

<a href="https://discord.gg/6dazXp6" target="_blank">
  <img alt="discord" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/minimal/social/discord-singular_vector.svg" height="64" align="right">
</a>
<a href="https://app.revolt.chat/invite/74TpERXA" target="_blank">
  <img alt="revolt" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/minimal/social/revolt-singular_vector.svg" height="64" align="right">
</a>

This is a simple plugin that adds support for the Presence Footsteps mod to ItemsAdder.

## The Goal

The goal is to add a way to automatically generate the `blockmap.json` file used by Presence Footsteps to override sounds for custom blocks.

## How to

> **Note**  
> Make sure to have used `/iazip` AT LEAST once before using this plugin, as it requires a valid `real_blocks_note_ids_cache.yml` to be present.

In order to use this plugin, open any YAML file in ItemsAdder that contains items with the `block` property set.

In the file, add the following (Example using `ruby_ore` in `/ItemsAdder/data/items_packs/itemsadder/blocks.yml`):  
```diff
items:
  ruby_ore:
    display_name: display-name-ruby_ore
    permission: ruby_ore
    resource:
      material: PAPER
      generate: true
      textures:
      - block/ruby_ore.png
  specific_properties:
    block:
      hardness: 3
      placed_model:
        type: REAL_NOTE
        break_particles_material: REDSTONE_BLOCK
      cancel_drops: true
      break_tools_blacklist:
      - WOODEN_PICKAXE
      - STONE_PICKAXE
      break_tools_whitelist:
      - PICKAXE
      - pickaxe
      - _hammer
+     pf_sound: 'ore'
```

After that, save the file(s) and run `/pfcreate` to create the `blockmap.json` file.  

> **Warning**  
> If such a file already exists will you need to add `--override` as an argument to the command to override it!
