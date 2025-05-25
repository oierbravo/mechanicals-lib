# Mechanicals Lib
Library for neoforge modding. Mainly, but not limited to, create addons.

## [Dedicated wiki](https://wiki.mechanicalmods.net/mods/mechanicals-lib/)

## Add as dependency
- `build.gradle` repository:
```
    maven {
        url "https://maven.fosil.eu/releases"
    }
```

- `build.gradle` dependency:
```
implementation("com.oierbravo.mechanicals:Mechanicals:${minecraft_version}-${mechanicals_version}")
```

- `gradle.properties` variables:
```
minecraft_version=1.20.1
mechanicals_version=4.28 #Use always latest.
```

## License
- Mechanicals Lib is licensed under the LGPL3 license. See [LICENSE](LICENSE) for more information.
- Certain sections of the code are from the Create mod, which is licensed under the MIT license. See Create's license for more information.