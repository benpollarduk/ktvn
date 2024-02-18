# Audio
Ktvn supports audio to allow rich visual novels to be created. Audio is roughly broken in to two categories - 
background music and sound effects.


## Background Music
In Ktvn background music is specified as part of the **Scene**. This is documented on the [Customising Scenes](customising-scenes.md) 
page.

## Sound Effects
Sounds effects can be played during any **Step**. All sound effects require an **AudioManager** to play.

### Audio Manager
The **AudioManager** is a class that manages some of the simple audio tasks in Ktvn. It depends on an **AudioAdapter**. 
The **DynamicGameConfiguration** provides an instance of **AudioAdapter** that is suitable for nearly all uses.

```kotlin
val configuration = DynamicGameConfiguration()
val audio = AudioManager(configuration.gameAdapter.audioAdapter)
```

The **AudioManager** can then be used to play sound effects with the **sfx** function. Here a **next** step is used to 
play *sfxWoosh*.

```kotlin
next { audio sfx sfxWoosh }
```

In this case *sfxWoosh** is a **ResourceSoundEffect**, but a **FileSoundEffect** could also be used.

```kotlin
val sfxWoosh = soundEffectFromResource("shuttleLaunch/audio/woosh.wav")
```