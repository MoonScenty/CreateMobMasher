# Create Mob Masher

**Create power goes in. Mob guts and loot come out. Simple as hell.**

Create Mob Masher is a NeoForge addon that bolts the rotational power system from
[Create](https://github.com/Creators-of-Create/Create) onto the murder machinery from
[Mob Grinding Utils](https://www.curseforge.com/minecraft/mc-mods/mob-grinding-utils).

Feed the machine enough RPM, shove MGU saw upgrades into it, and it repeatedly beats
the living shit out of anything standing inside it using an MGU fake player and Null Sword.

[한국어 README](README_KO.md)

## Requirements

- Minecraft 1.21.1
- Java 21
- NeoForge 21.1.233 or newer
- Create 6.0.10 or newer, but below 6.1.0
- Mob Grinding Utils 1.1.10 or newer

Create and Mob Grinding Utils are required. Trying to run this addon without either of
them is not going to end well.

## What This Bastard Does

- Accepts Create rotational power through the shaft on its bottom face.
- Refuses to do jack shit below **128 RPM**.
- Applies a stress impact of **16 SU/RPM**.
- Attacks every living entity inside its roughly one-block working area.
- Runs once every 30 ticks at 128 RPM and accelerates to once every 10 ticks at 256 RPM.
- Uses an MGU fake player and Null Sword, so kills behave like player attacks.
- Stores and applies Mob Grinding Utils saw upgrades.
- Returns every installed upgrade when removed or broken.
- Reports its status, required speed, stress impact, and upgrades through Create goggles.
- Includes a Create Ponder scene that demonstrates the whole bloody process.

## Using It

1. Place the Mechanical Mob Masher.
2. Connect Create rotational power to the shaft on its bottom face.
3. Give it at least 128 RPM. Less than that and the lazy lump of iron does nothing.
4. Put mobs inside the machine's working area.
5. Collect the drops with whatever contraption you prefer.

Hover over the item in an inventory and hold the Create Ponder key (`W` by default) to
see the machine, power requirements, upgrades, removal controls, and murder demonstration.

## Upgrades

Right-click the machine with an MGU saw upgrade to shove one into its corresponding
internal slot. The maximum count per upgrade type follows Mob Grinding Utils' server
configuration.

Supported upgrade effects:

- Sharpness
- Looting
- Fire Aspect
- Smite
- Bane of Arthropods
- Beheading

Sneak-right-click with an empty hand to yank an installed upgrade back out. Breaking the
machine spits every installed upgrade onto the ground instead of eating your expensive crap.

## Recipes

This mod intentionally provides **no built-in crafting recipe**. Modpack authors are
expected to define whatever cost fits their pack with a datapack, KubeJS, CraftTweaker,
or another recipe tool. That is a design decision, not a missing feature.

For development, testing, or packs that hand the machine out by other means:

```mcfunction
/give @s createmobmasher:mechanical_mob_masher
```

## Known Behavior

- The target list is every `LivingEntity` in range, including players, friendly mobs, and
  hostile mobs. Do not stand in the damn machine and then act surprised.
- This is an early public release. If users find a spectacular new way to break it, report
  the mess on the [GitHub issue tracker](https://github.com/MoonScenty/CreateMobMasher/issues).

## Building

Clone the repository and run:

```powershell
.\gradlew.bat build
```

The built JAR is placed in `build/libs`.

For a development client:

```powershell
.\gradlew.bat runClient
```

If Gradle's dependency cache shits the bed:

```powershell
.\gradlew.bat --refresh-dependencies
```

## License

This project is licensed under the [MIT License](LICENSE). Fork it, tear it apart, and
build whatever unholy contraption you want with it—just keep the copyright and license
notice intact.
