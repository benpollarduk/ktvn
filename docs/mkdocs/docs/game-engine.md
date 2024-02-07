# Game Engine

## Overview
The **GameEngine** is a critical part of Ktvn. Essentially, the GameEngine ties together a **Game** and the environment 
it executes in. The GameEngine is responsible for managing game input and output. The GameEngine interface is so 
important because it allows a **VisualNovel** to be written largely without a target platform in mind.
Included in the Ktvn repo are a couple of examples of GameEngine implementations.

### AnsiConsoleGameEngine
Included in Ktvn is an example implementation of a GameEngine that targets an ANSI compatible console, 
**AnsiConsoleGameEngine**. Running a visual novel in a basic terminal window with limited visuals seems pretty 
counterproductive, but it is a clear way of demonstrating some aspects of implementing a GameEngine.

### DebugGameEngine
In **app-ktvn-prototyper-swing** there is an example implementation of a GameEngine that targets Swing. This is used in 
the prototyper app. It's by no means intended as an engine to use in a production game, but serves the purpose of 
demonstrating some of the visual features on Ktvn and is the engine that drives the prototyper app which can be used 
for rough prototyping of games.

## Implementing a custom GameEngine
It's important to understand the role of the GameEngine so that rich novels can be written that target any platform. In 
this next section I'll describe how a GameEngine can be created to target a platform.

### Creating a new GameEngine
Start by creating a class and inheriting the GameEngine interface.

```kotlin
package com.github.benpollarduk.ktvn.example.engine

import com.github.benpollarduk.ktvn.logic.engines.GameEngine

class ExampleEngine : GameEngine {
}
```

Implement the interface to create stubs. We will discuss those in more detail in a bit.

```kotlin
package com.github.benpollarduk.ktvn.example.engine

import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.audio.VolumeManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.ProgressionController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.engines.GameEngine
import com.github.benpollarduk.ktvn.structure.*
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import com.github.benpollarduk.ktvn.text.log.Log

class ExampleEngine : GameEngine {
    override val log: Log
        get() = TODO("Not yet implemented")
    override val progressionController: ProgressionController
        get() = TODO("Not yet implemented")
    override val volumeManager: VolumeManager
        get() = TODO("Not yet implemented")

    override fun playSoundEffect(soundEffect: SoundEffect) {
        TODO("Not yet implemented")
    }

    override fun characterAsksQuestion(character: Character, question: Question) {
        TODO("Not yet implemented")
    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {
        TODO("Not yet implemented")
    }

    override fun getAnswerToQuestion(question: Question): Answer {
        TODO("Not yet implemented")
    }

    override fun characterSpeaks(character: Character, line: String) {
        TODO("Not yet implemented")
    }

    override fun characterThinks(character: Character, line: String) {
        TODO("Not yet implemented")
    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {
        TODO("Not yet implemented")
    }

    override fun characterAnimation(character: Character, animation: Animation) {
        TODO("Not yet implemented")
    }

    override fun characterMoves(character: Character, from: Position, to: Position, transition: LayoutTransition) {
        TODO("Not yet implemented")
    }

    override fun narratorNarrates(narrator: Narrator, line: String) {
        TODO("Not yet implemented")
    }

    override fun enterStory(story: Story) {
        TODO("Not yet implemented")
    }

    override fun exitStory(story: Story) {
        TODO("Not yet implemented")
    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        TODO("Not yet implemented")
    }

    override fun exitChapter(chapter: Chapter) {
        TODO("Not yet implemented")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        TODO("Not yet implemented")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        TODO("Not yet implemented")
    }

    override fun clearScene(scene: Scene) {
        TODO("Not yet implemented")
    }

    override fun enterStep(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken) {
        TODO("Not yet implemented")
    }

    override fun exitStep(step: Step, flags: Flags) {
        TODO("Not yet implemented")
    }
}
```

Next set up the **Log**, **ProgressionController** and **VolumeManager** properties. These can just be instantiated 
with default constructors.

```kotlin
override val log: Log = Log()
override val progressionController: ProgressionController = ProgressionController()
override val volumeManager: VolumeManager = VolumeManager()
```

The **Log** provides all logging for in game events so that users can review the story. If you use 
**DynamicGameConfiguration** this will be automatically populated, but if you make your own **GameConfiguration** you 
will need to populate the log yourself.

The **ProgressionController** controls progression through the story. This allows you to access **Auto** and **Skip** 
modes.

The **VolumeManager** gives you a central point to manage all volumes for music, sound effects and other audio. The 
**VolumeManager** does not directly control the system volume, it just provides a normalised value to represent the 
relative volume that audio should be played at for each type of audio.

Now it is just a case of writing some code for each stub. The stubs are described below.

#### playSoundEffect
Occurs when a sound effect should be played.

#### characterAsksQuestion
Occurs when a character asks a question.

#### narratorAsksQuestion
Occurs when a narrator asks a question.

#### getAnswerToQuestion
Occurs when an answer to a question should be determined.

#### characterSpeaks
Occurs when a character speaks.

#### characterThinks
Occurs when a character thinks.

#### characterShowsEmotion
Occurs when a character shows emotion.

#### characterAnimation
Occurs when a character starts an animation.

#### characterMoves
Occurs when a character moves between positions in a layout.

#### narratorNarrates
Occurs when a narrator narrates.

#### enterStory
Occurs when a story is entered.

#### exitStory
Occurs when a story is exited.

#### enterChapter
Occurs when a chapter is entered.

#### exitChapter
Occurs when a chapter is exited.

#### enterScene
Occurs when a scene is entered.

#### exitScene
Occurs when a scene is exited.

#### clearScene
Occurs when a scene should be cleared.

#### enterStep
Occurs when a step is entered.

#### exitStep
Occurs when a step is exited.

## Applying your GameEngine
Don't forget to apply your engine to the **GameConfiguration**. Unless you have implemented your own 
**GameConfiguration** this will likely be the **DynamicGameConfiguration**. After you have done this, you should be all set!

```kotlin
val configuration = DynamicGameConfiguration().also {
    it.engine = YourEngine()
}
```