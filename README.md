# Create Mob Masher

**Create power goes in. Mob guts and loot come out. Simple as hell.**

Create Mob Masher is a NeoForge addon that bolts the rotational power system from
[Create](https://github.com/Creators-of-Create/Create) onto the murder machinery from
[Mob Grinding Utils](https://www.curseforge.com/minecraft/mc-mods/mob-grinding-utils).

Feed the machine enough RPM, shove some upgrades into it, and it repeatedly beats the
living shit out of anything standing inside it using an MGU fake player and null sword.

[한국어 README](README_KO.md)

## Requirements

- Minecraft 1.21.1
- Java 21
- NeoForge 21.1.233 or newer
- Create 6.0.10 (below 6.1.0)
- Mob Grinding Utils 1.1.10 or newer

The development build also uses Ponder, Flywheel, and Registrate through Create's
toolchain. Check `gradle.properties` and `build.gradle` for the exact dependency versions.

## What This Bastard Does

- Accepts Create rotational power through the bottom shaft.
- Refuses to do jack shit below **128 RPM**.
- Applies a stress impact of **16 SU/RPM**.
- Attacks every living entity inside its roughly one-block working area.
- Speeds up from one attack cycle every 30 ticks at 128 RPM to every 10 ticks at 256 RPM.
- Uses a fake player, so kills behave like player attacks instead of generic magic damage.
- Stores and applies Mob Grinding Utils saw upgrades.
- Shows its speed, status, stress impact, and installed upgrades through Create goggles.

## Using It

1. Place the Mechanical Mob Masher.
2. Connect a Create shaft to its bottom face.
3. Give it at least 128 RPM. Less than that and the lazy piece of iron does nothing.
4. Put mobs inside the block's working area.
5. Watch it cave their shit in and collect the drops with whatever contraption you prefer.

There is currently no recipe data in this repository. For development or testing, get the
block with:

```mcfunction
/give @s createmobmasher:mechanical_mob_masher
```

## Upgrades

Right-click the machine with an MGU saw upgrade to shove one into the corresponding
internal slot. The maximum count per upgrade type follows Mob Grinding Utils' server
configuration.

Supported upgrade effects include:

- Sharpness
- Looting
- Fire Aspect
- Smite
- Bane of Arthropods
- Beheading

Sneak-right-click with an empty hand to yank the last installed upgrade back out. Break
the machine and it spits all installed upgrades onto the ground instead of eating your
expensive crap.

## Building

Clone the repository and run:

```powershell
.\gradlew.bat build
```

The built JAR will be placed in `build/libs`.

For a development client:

```powershell
.\gradlew.bat runClient
```

If Gradle's dependency cache shits the bed, try:

```powershell
.\gradlew.bat --refresh-dependencies
```

## Current Rough Edges

- No crafting recipe is included yet.
- The English language file still needs proper localization entries.
- The MGU saw mixin currently only prints a debug message and changes no behavior.
- The attack target list is every `LivingEntity` in range. Until filtering is added, do not
  stand in the damn machine and then act surprised.

## License

All Rights Reserved. See [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).
