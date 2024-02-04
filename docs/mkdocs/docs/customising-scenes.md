# Customising Scenes

## Overview
A **Scene** is a section of a **Chapter**. It can be used to tell the story and describe what the player should see and 
hear.

## Setting the name
A **Scene** has a name. This can be useful for referencing it and depending on the engine implementation may be 
displayed when the scene is executed.

```kotlin
val scene = scene {
    this name "In the Mansion"
}
```

## Setting the background
A **Scene** has a background. There a lots of ways backgrounds can be specified, and the **Background** interface adds 
extensibility if required.

Backgrounds can be specified from a path to a file.

```kotlin
val mansion = backgroundFromFile("mansion.jpg")
val scene = scene {
    this background mansion
}
```

Backgrounds can be specified from a resource key.

```kotlin
val mansion = backgroundFromResource("mansion.jpg")
val scene = scene {
    this background mansion
}
```

Simple solid color backgrounds can also be used.

```kotlin
val black = backgroundFromColor(Color.BLACK)
val scene = scene {
    this background black
}
```

## Setting the music
Each **Scene** can have background music.

Audio can be specified from files.

```kotlin
val mansionTheme = trackFromFile("mansion.wav")
val scene = scene {
    this music mansionTheme
}
```

Audio can be specified from resources.

```kotlin
val mansionTheme = trackFromResource("mansion.wav")
val scene = scene {
    this music mansionTheme
}
```

And silence can be used when no music should play.

```kotlin
val scene = scene {
    this music silence
}
```

## Transitions
Transitions can be applied to a **Scene.** Transitions allow fade ins and fade outs etc. The **SceneTransition** 
interface provides extensibility if required.

Here a fade in from black over 250ms has been specified.

```kotlin
val scene = scene {
    this transitionIn FadeIn(Color.BLACK, 250L)
}
```

Here a fade out to black over 250ms has been specified.

```kotlin
val scene = scene {
    this transitionOut FadeOut(Color.BLACK, 250L)
}
```

## Layout
Layout specifies how **Characters** are positioned for a scene, and how they move between positions.

A layout must be created and configured so that it can interact with the **GameEngine**.

```kotlin
val configuration = DynamicGameConfiguration()
val scene = scene {
    this layout createLayout {
        this configure configuration.gameAdapter.layoutAdapter
    }
}
```

The **transition** keyword specifies how characters should move around the layout. Here a **Slide** transition has been 
specified with a duration of 100ms. The **LayoutTransition** interface adds extensibility if required.

```kotlin
val scene = scene {
    this layout createLayout {
        this transition Slide(100L)
    }
}
```

Characters can be added to a layout. In this example the character has been added to the left of the screen. The 
character is not visible as it is off of the screen, but is ready to be pulled in to view when required.

```kotlin
val configuration = DynamicGameConfiguration()
val ben = Character("Ben", configuration.gameAdapter.characterAdapter)
val scene = scene {
    this layout createLayout {
        this addLeftOf ben
    }
}
```

Characters can then move in a layout. The above examples can be combined to slide a character from the outside of the 
left hand side of the screen to the center.

```kotlin
val configuration = DynamicGameConfiguration()
val ben = Character("Ben", configuration.gameAdapter.characterAdapter)
val scene = scene {
    this layout createLayout {
        this configure configuration.gameAdapter.layoutAdapter
        this transition Slide(100L)
        this addLeftOf ben
        this moveCenter ben
    }
}
```