<div align="center">
    
# Ktvn
Ktvn is a framework for writing visual novels in Kotlin or Java. It is largely based on Kotlins strong Domain Specific Language
(DSL) capabilities. The aim of Ktvn is to provide a flexible framework for writing visual novels that can be pulled in 
to other projects. Ktvn provides a quick, natural and flexible syntax that is easy to pick up and simple to maintain, 
while retaining Kotlins powerful and feature rich syntax.

[![main-ci](https://github.com/benpollarduk/ktvn/actions/workflows/main-ci.yml/badge.svg?branch=main)](https://github.com/benpollarduk/ktvn/actions/workflows/main-ci.yml)
[![codecov](https://codecov.io/gh/benpollarduk/ktvn/graph/badge.svg?token=H9RCA8F7DE)](https://codecov.io/gh/benpollarduk/ktvn)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=benpollarduk_ktvn&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=benpollarduk_ktvn)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=benpollarduk_ktvn&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=benpollarduk_ktvn)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=benpollarduk_ktvn&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=benpollarduk_ktvn)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=benpollarduk_ktvn&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=benpollarduk_ktvn)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=benpollarduk_ktvn&metric=bugs)](https://sonarcloud.io/summary/new_code?id=benpollarduk_ktvn)
[![GitHub release](https://img.shields.io/github/release/benpollarduk/ktvn.svg)](https://github.com/benpollarduk/ktvn/releases)
[![License](https://img.shields.io/github/license/benpollarduk/ktvn.svg)](https://opensource.org/licenses/MIT)
[![Documentation Status](https://img.shields.io/badge/docs-latest-brightgreen.svg)](https://benpollarduk.github.io/ktvn-docs/)

</div>

## Getting Started

### Clone the repo
Clone the repo to the local machine.
```bash
git clone https://github.com/benpollarduk/ktvn.git
```

### Hello World
```kotlin
// a ktvn visual novel requires a configuration, DynamicGameConfiguration provides a default that can be tailored to the UI being used
val configuration = DynamicGameConfiguration()

// the configuration requires an engine to process input and output. the engine itself will vary depending on the UI framework being targeted
configuration.engine = AnsiConsoleGameEngine()

// the narrator provides a class that can narrate the story
val narrator = Narrator(configuration.gameAdapter.narratorAdapter)

// create a simple story
val story = story {

    // add a single chapter
    this add chapter {

        // add a new scene
        this add scene {

            // set the steps that make up the scene
            this steps listOf(

                // include a simple narration step
                next { narrator narrates "Welcome to a Ktvn visual novel!" }
            )
        }
    }
}

// a visual novel provides a wrapper for a story that hooks together the story and the configuration
val visualNovel = VisualNovel.create(story, configuration)

// a game provides an object that is used to control execution of the visual novel
val game = Game(visualNovel)

// execute the game synchronously
GameExecutor.execute(game)
```

### Example visual novels
The quickest way to start getting to grips with the structure of Ktvn and the DSL is by taking a look at the examples. 
Example visual novels are provided in the [ktvn-exmples](https://github.com/benpollarduk/ktvn/tree/main/ktvn-examples/src/main/kotlin/com/github/benpollarduk/ktvn/examples) directory 
and have been designed with the aim of showcasing the various features.

### Running the examples
The included examples can be run with a couple of included apps:

#### Prototyping console
The prototyping console is useful for running through visual novels through a basic console application.

* Build the prototyping console.
```bash
./gradlew :app-ktvn-prototyper-console:build
```
* Run an example through the prototyping console.
```bash
cd app-ktvn-prototyper-console/build/libs
java -jar app-ktvn-prototyper-console-all.jar
```

#### Prototyping Swing UI
The prototyping Swing app provides a simple application that supports the core Ktvn concepts.

* Build the prototyping Swing app.
```bash
./gradlew :app-ktvn-prototyper-swing:build
```
* Run an example through the prototyping Swing app.
```bash
cd app-ktvn-prototyper-swing/build/libs
java -jar app-ktvn-prototyper-swing-all.jar
```

## Story Structure
A Ktvn visual novel starts with a **Story**. A Story contains one or more **Chapters**. Each Chapter contains one or 
more **Scenes**. Each Scene contains one or more **Steps**. There are several types of Step, and Step is extensible 
so that the DSL can be customised.

For example:

```
Story
├── Chapter
│   ├── Scene
│   |   ├── Step
│   |   ├── Step
│   |   ├── Step
|   ├── Scene
│   |   ├── Step
│   |   ├── Step
├── Chapter
│   ├── Scene
│   |   ├── Step
|   ├── Scene
│   |   ├── Step
```   
  
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

## Execution
Stories can be executed as a **Game**. Games must be executed through the **GameExecutor**, an object dedicated to game 
management. The game executor can run games synchronously or asynchronously.

```kotlin
// execute the game synchronously
GameExecutor.execute(game)
```

Or:

```kotlin
// execute the game asynchronously
GameExecutor.executeAysnc(game) {
    // TODO: handle game completion
}
```

The constructor for Game objects accepts an instance of **VisualNovel**. The VisualNovel is a critical component of 
Ktvn, it essentially wraps a Story and a **GameConfiguration** into a single discoverable file. The GameConfiguration 
ties together how the story and the UI interact with one another. Please see the **Integration** section of this readme 
for more information.

## Persistence
Persistence is handled in three distinct parts, **GameSave**, **RestorePoint** and **StepTracker** .

### GameSave
The users settings, endings reached and total seconds played are saved in a **GameSave**.
```kotlin
val gameSave = game.getGameSave()
GameSaveSerializer.toFile(gameSave, path)
```

### RestorePoint
Progress in a game can be persisted as a **RestorePoint**. A restore point can be generated at any point before, 
during or after a games execution and persisted to file using the **RestorePointSerializer**. This stores the users 
current position in the game, flags and has a name, a creation date and time and a thumbnail.
```kotlin
val restorePoint = game.getRestorePoint("File1")
RestorePointSerializer.toFile(restorePoint, path)
```

### StepTracker
The **StepTracker** tracks which steps have been viewed by the player. This is important as it allows the skip feature 
to skip viewed steps on a subsequent play through. As default a **StepIdentifierTracker** is provided and records steps 
with a deterministic identifier.
```kotlin
gameEngine.stepTracker.persist(path)
```

## Core DSL
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
Or a character may think:
```kotlin
next { morgana thinks "I could never tell Michel I don't actually think he is a fool." }
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
next { audio sfx sfxFromResource("crash") }
```

### then
then is very similar to next, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
then {
    this name "Michel shows anger"
    this does {
        michel looks angry
    }
}
```

### choice
choice allows the user to present a question to the user and receive an answer. A character or the narrator can ask a 
multiple choice question:
```kotlin
choice {
    morgana asks question {
        this line "Why we do, don't we dear?"
        this option answer {
            this line "Of course."
            this does { flags ->
                flags setTrue "Michel likes Morgana"
            }
        }
        this option answer {
            this line "I hate you!"
            this does { flags ->
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

### decision
decision is very similar to choice, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
decision {
    this name "Michel's feeling towards Morgana"
    this does {
        morgana asks question {
            this line "Why we do, don't we dear?"
            this option answer {
                this line "Of course."
                this does { flags ->
                    flags setTrue "Michel likes Morgana"
                }
            }
            this option answer {
                this line "I hate you!"
                this does { flags ->
                    flags setTrue "Michel hates Morgana"
                }
            }
        }
    }
}
```

### conditional
conditional allows a step to only be invoked if a specified condition is met.
```kotlin
conditional {
    this condition "Michel likes Morgana"
    this does {
        morgana looks amused
        morgana says "Fool, I despise you!"
    }
    this returns Continue
}
```
The **condition** keyword specifies the flag. If that flag is set to true then the script specified by the **does** 
keyword will be executed. Lastly the **returns** keyword specifies the result of the step so that the story can 
continue, branch or end as required.

### pause
pause is a step that prevents the story from progressing for the specified duration.
```kotlin
pause {
    this seconds 5
}
```
The **seconds** keyword allows the delay time to be specified, in seconds. Shorter delays can be specified in
milliseconds using the **milliseconds** keyword.

### clear
clear is a step that signals that the current scene should be cleared. The engine is responsible for actioning this in a way that makes sense for the executing game.
```kotlin
clear { }
```
Suggested use cases are for clearing the text from a narrative scene, or removing all dialog from a dialog scene.

### interactive
interactive is a step that adds extensibility to the game in the form of allowing other components to be called. For 
example, some visual novels may contain mini-games. Providing the mini game implements or is wrapped by 
**InteractiveComponent** it can be invoked in a step.
```kotlin
interactive {
    this element component
    this args arrayOf("args1", "args2")
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

### end
end is a simple step that signifies that an ending has been reached.
```kotlin
end {
    this ending Ending("True End.", 1)
}
```
The ending that was reached can be specified with the **ending** keyword.

For further examples please see the ktvn-example directory in the repo.

## Progression
Progression through a story is controlled by the **ProgressionController**, which is part of the **GameEngine**. The 
following progression modes are supported:
* Wait for acknowledgement - the user must acknowledge each step.
* Skip - previously viewed steps will be skipped.
* Auto - the step will be acknowledged after a specified time.

## Integration
Ktvn provides a structure, DSL and flow control for creating visual novels, but it does not provide a framework for 
creating UIs and managing assets. Many frameworks for this exist. To integrate with a story a **GameConfiguration** is 
required. The easiest way of achieving this is by using **DynamicGameConfiguration** with a **GameEngine**. 
Please see **AnsiConsoleGameEngine** for a simple example that demonstrates how to create a game engine that integrates with an ANSI compatible console.

The UI and the GameEngine will be unique for each project. In app-ktvn-prototyper-console the UI is provided by the 
terminal and **AnsiConsoleGameEngine** interacts directly with this.

![ktvn-sequencing-overview-components.png](docs%2Foverview%2Fktvn-sequencing-overview-components.png)

The following sequence diagram shows the sequence of starting a game and a character speaking then asking a question:

![ktvn-sequencing-overview.png](docs%2Foverview%2Fktvn-sequencing-overview.png)

## Prototyping
Ktvn is focused on providing a framework for writing visual novels that can be pulled in to other frameworks rather than 
attempting to provide a full solution for writing visual novels from start to finish. This puts some emphasis on writing 
an engine to fit the target framwork but allows for bespoke UI solutions to be created. However, a swing prototyping app 
is included in the repo to help with the prototyping phases of development. Please see 
[app-ktvn-prototyper-swing](https://github.com/benpollarduk/ktvn/tree/main/app-ktvn-prototyper-swing) for more information.

![ktvn_prototyper_example](https://github.com/benpollarduk/ktvn/assets/129943363/99be2a75-7255-43aa-a17a-120aec1d5a8f)

The source for some of the above scene:

```kotlin
internal fun onTheLaunchPad(): Scene {
    return scene {
        this name "On the launch pad"
        this background shuttleDay
        this music shuttleDayMusic
        this layout createLayout {
            this addLeftOf sophie
            this addRightOf toki
            this configure configuration.gameAdapter.layoutAdapter
        }
        this steps listOf(
            next {
                layout moveCenter sophie
                sophie looks normal
                sophie says "Where has that fool gotten to now?"
            },
            next { audio sfx sfxWoosh },
            next {
                layout moveLeft sophie
                layout moveRight toki
                toki looks normal
                toki says "Here I am!"
                toki says "Ready for duty!"
            },
            next {
                sophie looks happy
                sophie says "Ever the fool."
            }
        )
    }
}
```

## Documentation
Please visit [https://benpollarduk.github.io/ktvn-docs/](https://benpollarduk.github.io/ktvn-docs/) to view the Ktvn documentation.

Please visit [https://benpollarduk.github.io/ktvn-api-docs/](https://benpollarduk.github.io/ktvn-api-docs/) to view the Ktvn Api documentation.

## For Open Questions
Visit https://github.com/benpollarduk/ktvn/issues
