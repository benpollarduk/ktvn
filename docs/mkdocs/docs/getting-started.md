## Adding the package to your project
You need to pull Ktvn into your project. The easiest way to do this is to add the package. The latest package and 
installation instructions are available [here](https://github.com/benpollarduk/ktvn/packages/).

## First Visual Novel
Once the package has been installed it's time to jump in and start building your first visual novel.

### Setup
To start with create a new Console application. It should look something like this:

```kotlin
package com.github.benpollarduk.ktvn.gettingstarted

fun main(args: Array<String>) {

}
```

### Adding the GameEngine
Every Ktvn game requires a **GameEngine**. The only engine currently included in the Ktvn core library is
**AnsiConsoleGameEngine**. This engine allows a visual novel to be executed on an ANSI compatible console. 

```kotlin
val engine = AnsiConsoleGameEngine()
```

### Adding the GameConfiguration
Every Ktvn game requires a **GameConfiguration**. The configuration ties together many of the concepts required to 
execute a game. For nearly all applications the **DynamicGameConfiguration** is a good match because it allows 
properties to be specified after its instantiation. Let's add one in the **main** function and apply the engine.

```kotlin
val configuration = DynamicGameConfiguration().also {
    it.engine = engine
}
```

### Adding a Character and a Narrator
All visual novels will require at least a **Narrator** or a **Character** to tell the story. Let's add one of each to 
the **main** function, after the engine.

```kotlin
val ben = Character("Ben", configuration.gameAdapter.characterAdapter)
```

Here I've added a character called Ben (of course) and specified that the adapter is provided by the **configuration**. 
The adapter allows the Character to interact with the engine.

Next I'll add a **Narrator**.

```kotlin
val narrator = Narrator(configuration.gameAdapter.narratorAdapter)
```

I've specified that the adapter is provided by the **configuration**. The adapter allows the Narrator to interact with 
the engine. 


### Creating a basic visual novel structure
The next thing to do is to add the structure of the novel itself. We can use the DSL to simplify the process. To start 
with, let's add a story in the **main** function. The story will only contain a single chapter, which contains a single 
scene.

```kotlin
val story = story {
    this add chapter {
        this add scene {

        }
    }
}
```
**Story**, **Chapter** and **Scene** can all have a name, lets name the scene.

```kotlin
val story = story {
    this add chapter {
        this add scene {
            this name "Welcome to Ktvn"
        }
    }
}
```

Each scene that contains Characters should have a layout to help with the positioning of characters. The layout needs 
to be configured so that the game engine can respond to character movements.

```kotlin
val story = story {
    this add chapter {
        this add scene {
            this name "Welcome to Ktvn"
            this layout createLayout {
                this configure configuration.gameAdapter.layoutAdapter
            }
        }
    }
}
```

### Adding some steps
Each scene can contain one or more steps. Let's add s simple step to introduce the story.

```kotlin
val story = story {
    this add chapter {
        this add scene {
            this name "Welcome to Ktvn"
            this layout createLayout {
                this configure configuration.gameAdapter.layoutAdapter
            }
            next { narrator narrates "Welcome to the story!" }
        }
    }
}
```

Next, we could move the character in to view.

```kotlin
val story = story {
    this add chapter {
        this add scene {
            this name "Welcome to Ktvn"
            this layout createLayout {
                this configure configuration.gameAdapter.layoutAdapter
            }
            next { narrator narrates "Welcome to the story!" }
            next { layout moveLeft ben }
        }
    }
}
```

And then we can make the character speak.

```kotlin
val story = story {
    this add chapter {
        this add scene {
            this name "Welcome to Ktvn"
            this layout createLayout {
                this configure configuration.gameAdapter.layoutAdapter
            }
            next { narrator narrates "Welcome to the story!" }
            next { layout moveLeft ben }
            next { ben says "Hi." }
        }
    }
}
```

And then make the character smile.

```kotlin
val story = story {
    this add chapter {
        this add scene {
            this name "Welcome to Ktvn"
            this layout createLayout {
                this configure configuration.gameAdapter.layoutAdapter
            }
            next { narrator narrates "Welcome to the story!" }
            next { layout moveLeft ben }
            next { ben says "Hi." }
            next { ben looks happy }
        }
    }
}
```
There is much more that can be done, but this is a simple example just to get started.

### Executing the game
There is a small amount to do to make the story executable. First, it needs to be wrapped in to a **VisualNovel**. Then 
a new **Game** needs to be made from the VisualNovel so that the story can be executed.

```kotlin
val novel = VisualNovel.create(story, configuration)
val exampleGame = Game(novel)
```

The **GameExecutor** class can execute instances of **Game**. Games can be executed either synchronously or 
asynchronously.

```kotlin
GameExecutor.executeAysnc(exampleGame)
```

A quirk of the **AnsiConsoleEngine** is that we need to start processing the input from the standard input to be able 
to interact with the game. We will need to stop processing this input when the game execution ends. We will start 
processing the input when the game begins and stop when execution finishes.

```kotlin
GameExecutor.executeAysnc(exampleGame) {
        engine.endProcessingInput()
    }

engine.beginProcessingInput()
```

### Bringing it all together
That was a lot to go through, but results in only a small amount of code. It should look like this:

```kotlin
package com.github.benpollarduk.ktvn.prototyper.console

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotions
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.VisualNovel
import com.github.benpollarduk.ktvn.logic.configuration.DynamicGameConfiguration
import com.github.benpollarduk.ktvn.logic.engines.ansiConsole.AnsiConsoleGameEngine
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.Story
import com.github.benpollarduk.ktvn.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

fun main(args: Array<String>) {
    val engine = AnsiConsoleGameEngine()
    val configuration = DynamicGameConfiguration().also {
        it.engine = engine
    }

    val ben = Character("Ben", configuration.gameAdapter.characterAdapter)
    val narrator = Narrator(configuration.gameAdapter.narratorAdapter)

    val story = story {
        this add chapter {
            this add scene {
                this name "Welcome to Ktvn"
                this layout createLayout {
                    this configure configuration.gameAdapter.layoutAdapter
                }
                next { narrator narrates "Welcome to the story!" }
                next { layout moveLeft ben }
                next { ben says "Hi." }
                next { ben looks Emotions.happy }
            }
        }
    }

    val novel = VisualNovel.create(story, configuration)
    val exampleGame = Game(novel)

    GameExecutor.executeAysnc(exampleGame) {
        engine.endProcessingInput()
    }

    engine.beginProcessingInput()
}
```

Simply build and run the application and congratulations, you have a working Ktvn visual novel!