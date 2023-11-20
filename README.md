# Introduction 
Ktvn is a framework for writing visual novels in Kotlin. It is largely based on Kotlins strong Domain Specific Language
(DSL) capabilities. The aim of Ktvn is to provide a flexible framework for writing visual novels that can be pulled in 
to other projects. Ktvn provides a quick, natural and flexible syntax that is easy to pick up and simple to maintain, 
while retaining Kotlins powerful and feature rich syntax. 

# Documentation
Please visit [https://benpollarduk.github.io/ktvn-docs/](https://benpollarduk.github.io/ktvn-docs/) to view the Ktvn documentation.

# Getting Started
* Clone the repo
* Build and run the included terminal application:
```bash
./gradlew clean build
cd app-ktvn-example-console/build/libs
java -jar app-ktvn-example-console-all.jar
```

# Hello World
```kotlin
val story = story {
    it add chapter {
        it add scene {
            it steps listOf(
                next { narrator narrates "Welcome to a Ktvn visual novel!" }
            )
        }
    }
}
```
# Story Structure
A Ktvn visual novel starts with a **Story**. A Story contains one or more **Chapters**. Each Chapter contains one or 
more **Scenes**. Each Scene contains one or more **Steps**. There are several types of Step, and Step is extensisible 
so that the DSL can be customised.

For example:

* Story
  * Chapter
    * Scene
      * Step
      * Step
    * Scene
      * Step
      * Step
  * Chapter
    * Scene
      * Step
      * Step       
  
Simple classes and DSL exist to support characters, narration, choices, flags, emotions and positioning of characters 
and more. Emotions and character positions are fully and easily extensible. When a characters emotion  or position 
changes a listener is invoked, so regardless of the UI system being used to render the visual novel these events can be 
heard and invoked. Each event requires an acknowledgment before the story continues, so flow control is easy. 
Listeners are provided for:
* Speak - when a character speaks.
* Narrate - when the narrator narrates.
* Move - when a characters position changes.
* Emote - when a characters emotion changes.
* Animate - when a characters animation changes.
* Ask - when either the narrator or a character asks a question.
* Scene - when scenes transition.
* Chapter - when chapters transition.
* Audio - when audio is changed.

# Execution #
Stories can be executed as a **Game**. Games must be executed through the **GameExecutor**, an object dedicated to game 
management. The game executor can run games synchronously or asynchronously.
```kotlin
// create a story
val story = story { story ->
    story add chapter { chapter ->
        chapter add scene { scene ->
            scene steps listOf(
                end {
                    it ending default
                }
            )
        }
    }
}

// create a game
val game = Game(story, gameConfiguration, Save.empty)

// execute the game synchronously
GameExecutor.execute(game)
```
The constructor for Game objects accepts an instance of **GameConfiguration**. This is a critical object and ties 
together how the game and the UI interact with one another. Please see the **Integration** section of this readme for
more information.

# Persistence #
Persistence is handled in 3 parts, **GameSave**, **RestorePoint** and **StepTracker** .

### GameSave ###
The users settings, endings reached and total seconds played are saved in a **GameSave**.
```kotlin
val gameSave = game.getGameSave()
GameSaveSerializer.toFile(gameSave, path)
```

### RestorePoint ###
Progress in a game can be persisted as a **RestorePoint**. A restore point can be generated at any point before, 
during or after a games execution and persisted to file using the **RestorePointSerializer**. This stores the users 
current position in the game, flags and has a name, a creation date and time and a thumbnail.
```kotlin
val restorePoint = game.getRestorePoint("File1")
RestorePointSerializer.toFile(restorePoint, path)
```

### StepTracker ###
The **StepTracker** tracks which steps have been viewed by the player. This is important as it allows the skip feature 
to skip viewed steps on a subsequent play through. As default a **StepIdentifierTracker** is provided and records steps 
with a deterministic identifier.
```kotlin
StepIdentifierTrackerSerializer.toFile(gameEngine.stepTracker, path)
```

# Core DSL
The Ktvn DSL is simple but powerful. Each step in a story has access to the parent story's flags, which allows variables 
such as user responses to be captured and made accessible to other steps. **Ktvn is in the very early stages of 
development and the DSL in particular may change.**

### next ###
next is a simple step to allow one or more actions to take place. For example, the narrator can say a line:
```kotlin
next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
```
Or a character may speak:
```kotlin
next { morgana says "The two of us are cursed to spend eternity in this mansion." }
```
Characters can show emotions:
```kotlin
next { michel looks concerned }
```
Characters can be animated:
```kotlin
next { michel begins shaking }
```
Change position on the screen:
```kotlin
next { layout moveRight morgana }
```
Or play a sound effect:
```kotlin
next { audio sfx "crash" }
```

### then ###
then is very similar to next, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
then {
    it name "Michel shows anger"
    it does {
        michel looks angry
    }
}
```

### choice ###
choice allows the user to present a question to the user and receive an answer. A character or the narrator can ask a 
multiple choice question:
```kotlin
choice {
    morgana asks question { question ->
        question line "Why we do, don't we dear?"
        question option answer { answer ->
            answer line "Of course."
            answer does { flags ->
                flags setTrue "Michel likes Morgana"
            }
        }
        question option answer { answer ->
            answer line "I hate you!"
            answer does { flags ->
                flags setTrue "Michel hates Morgana"
            }
        }
    }
}
```
Each choice can have multiple options. Each option has an optional script specified by the **does** keyword. 
Notice the use of the **flags** class. A single instance of Flags exists for each game and can be used to pass values 
between steps. Each flag has a string key and a boolean value. If a flag does not exist when it is read then false will be 
returned as default. In this 
case a flag is set to register the option that the user picked.

### decision ###
decision is very similar to choice, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
decision { decision ->
    decision name "Michel's feeling towards Morgana"
    decision does {
        morgana asks question { question ->
            question line "Why we do, don't we dear?"
            question option answer { answer ->
                answer line "Of course."
                answer does { flags ->
                  flags setTrue "Michel likes Morgana"
                }
            }
            question option answer { answer ->
                answer line "I hate you!"
                answer does { flags ->
                  flags setTrue "Michel hates Morgana"
                }
            }
        }
    }
}
```

### conditional ###
conditional allows a step to only be invoked if a specified condition is met.
```kotlin
conditional {
    it condition "Michel likes Morgana"
    it does {
        morgana looks amused
        morgana says "Fool, I despise you!"
    }
    it returns Continue
}
```
The **condition** keyword specifies the flag. If that flag is set to true then the script specified by the **does** 
keyword will be executed. Lastly the **returns** keyword specifies the result of the step so that the story can 
continue, branch or end as required.

### pause ###
pause is a step that prevents the story from progressing for the specified time, in milliseconds.
```kotlin
pause {
    it seconds 5
}
```
The **seconds** keyword allows the delay time to be specified, in seconds. Shorter delays can be specified in
milliseconds using the **milliseconds** keyword.

### clear ###
clear is a step that signals that the current scene should be cleared. How this is interpreted is up to the calling UI.
```kotlin
clear { }
```
Suggested use cases are for clearing the text from a narrative scene, or removing all dialog from a dialog scene.

## interactive ##
interactive is a step that adds extensibility to the game in the form of allowing other components to be called. For 
example, some visual novels may contain mini-games. Providing the mini game implements or is wrapped by 
**InteractiveComponent** it can be invoked in a step.
```kotlin
interactive {
    it element component
    it args arrayOf("args1", "args2")
}
```
An example of wrapping a component:
```kotlin
val component = object: InteractiveComponent {
    override fun invoke(args: Array<String>, flags: Flags, cancellationToken: CancellationToken): StepResult {
        /* invoke your component here.
           for example, you could:
            -invoke a mini-game
            -check something on the operating system and set a flag
            -play a video
            -anything else, it is up to you.
          The cancellationToken can be used to signal cancellation to your component.
          This function will need to return a StepResult which can be used to signal back to the story how it should 
          continue following execution of the component.
        */
        return StepResult.Continue
    }
}
```

### end ###
end is a simple step that signifies that an ending has been reached.
```kotlin
end {
    it ending Ending("True End.", 1)
}
```
The ending that was reached can be specified with the **ending** keyword.

For further examples please see the ktvn-example directory in the repo.

# Progression #
Progression through a story is controlled by the **ProgressionController**, which is part of the **GameEngine**. The 
following progression modes are supported:
* Wait for acknowledgement - the user must acknowledge each step.
* Skip - previously viewed steps will be skipped.
* Auto - the step will be acknowledged after a specified time.

# Integration #
Ktvn provides a structure, DSL and flow control for creating visual novels, but it does not provide a framework for 
creating UIs and managing assets. Many frameworks for this exist. To integrate with a story a **GameConfiguration** is 
required. The easiest way of achieving this is by using **StandardGameConfiguration** with a **GameEngine**. 
Please see **AnsiConsoleGameEngine** in app-ktvn-example-console for a simple example that demonstrates how to 
create a game engine that integrates with an ANSI compatible console.

The UI and the GameEngine will be unique for each project. In app-ktvn-example-console the UI is provided by the 
terminal and **AnsiConsoleGameEngine** interacts directly with this.
![ktvn-sequencing-overview-components.png](docs%2Fktvn-sequencing-overview-components.png)

The following sequence diagram shows the sequence of starting a game and a character speaking then asking a question:
![ktvn-sequencing-overview.png](docs%2Fktvn-sequencing-overview.png)

# For Open Questions
Visit https://github.com/benpollarduk/ktvn/issues
