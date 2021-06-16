<img src="./src/main/resources/assets/libzoomer/icon.png" align="right" width="128px"/>

# LibZoomer

A library that allows other mods to create zooms and custom spyglasses easily. The current supported version is Minecraft 1.17, since that version introduces spyglasses.

## Usage

**WARNING:** This API is still not polished enough for a 1.0.0 release, expect breaking changes on the 0.X.X releases.

Unfortunately, this library requires Jitpack in order to be used on another mod. If it fails, consider using local Maven on the source code of a released version.

LibZoomer provides zooming through zoom instances, which contains three sub-instances: transition modes, mouse modifiers and zoom overlays. A zoom instance can be created with different subinstances, which can be either provided by the library or implemented by the mod itself. In order to a zoom instance to be functional, it must be registered by the ZoomRegistry. In order to make the instance zoom in, the instance's zoom state must be set to true, which can be done using the `setZoom` method, the vice-versa can be done by setting it to false.

A better guide will be written later, feel free to take a look on this mod's testmod if you want an example!

## Download

This mod is meant to be JiJ'd by mods, so you wouldn't need to download it at all in most cases. If you really need to download it, it's available in the following services:

- [Modrinth (recommended)](https://modrinth.com/mod/libzoomer)
- [GitHub Releases (alternative)](https://github.com/EnnuiL/LibZoomer/releases)

## License

This mod is licensed under the MIT license. You can freely Jar-in-Jar this mod on any mod with no permission. Usage of this mod's code on other projects or derivatives of this mod is allowed as long as attribution is given.
