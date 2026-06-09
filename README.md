# IAxPresenceFootsteps

<a href="https://discord.gg/6dazXp6" target="_blank">
  <img alt="discord" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/minimal/social/discord-singular_vector.svg" height="64" align="right">
</a>

IAxPresenceFootsteps is a simple plugin that allows you to quickly and easily create a `blockmap.json` file for the `PresenceFootsteps` mod, to support stepping sounds for ItemsAdder's Custom Blocks.

## How it works

All you have to do, is add a `pf_sound` option to the `specific_properties.block` section of your custom item:
```yaml
info:
  namespace: example

items:
  example_block:
    display_name: "Example"
    graphics:
      texture: block/example
    specific_properties:
      block:
        placed_model:
          type: REAL_NOTE
        pf_sound: hardmetal # Add this!
```

After that, execute `/iaxpf create` to automatically create a new `blockmap.json` file.  
This blockmap file will contain all the custom ItemsAdder Blocks, which contain the `pf_sound` option and store it in `/plugins/ItemsAdder/contents/presencefootsteps/resourcepack/presencefootsteps/config/blockmap.json`

All that is left to do, is executing `/iazip` and the blockmap will be included in the resource pack.

## Commands

### `/iaxpf create [--verbose]`

Creates the blockmap.json file. If one already exists will it be overridden.  
Adding the `--verbose` flag will make the plugin provide a lot more info, such as what sounds it adds for what custom block (And vanilla blockstate), or what blocks got skipped and why.

## Known Issues/Limitations

### Blocks created from `directional_mode` aren't included

This issue is limited to versions before 4.0.17 of ItemsAdder.  
Due to a lack of API in older versions can the plugin not obtain the actual config used for blocks generated from the `directional_mode` option.  
Your best option is to update ItemsAdder to 4.0.17 or newer.
