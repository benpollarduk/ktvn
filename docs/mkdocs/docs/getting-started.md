## Adding the package to your project
You need to pull Ktvn into your project. The easiest way to do this is to add the package. The latest package and 
installation instructions are available [here](https://github.com/benpollarduk/ktvn/packages/).

## First Visual Novel
Once the package has been installed it's time to jump in and start building your first visual novel.

### Setup
To start with create a new Console application. It should look something like this:

```kotlin
package com.github.benpollarduk.ktvn.gettingstarted

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

    }
}
```

### Adding the configuration
Every Ktvn game requires a **GameConfiguration**. The configuration ties together many of the concepts required to execute a game. For nearly all applications the **DynamicGameConfiguration** is a good match because it allows properties to specified after its instantiation. Let's added one to the **main** function.

```kotlin
val configuration = DynamicGameConfiguration()
```

### Adding the engine
Every Ktvn game requires a **GameEngine**. The only engine currently included in the Ktvn core library is **AnsiConsoleGameEngine**. This engine allows a visual novel to be executed on an ANSI compatible console. The engine is specified on the configuration added in the previous step.

```kotlin
configuration.engine = AnsiConsoleGameEngine()
```

### Adding a Character and a Narrator
All visual novels will require at least a **Narrator** or a **Character** to tell the story. Let's add one of each to the **main** function, after the engine.

```kotlin
val ben = Character("Ben", configuration.gameAdapter.characterAdapter)
```

Here I've added a character called Ben (of course) and specified that the adapter is provided by the **configuration**. The adapter allows the Character to interact with the engine.

Next I'll add a **Narrator**.

```kotlin
val narrator = Narrator(configuration.gameAdapter.narratorAdapter)
```

I've specified that the adapter is provided by the **configuration**. The adapter allows the Narrator to interact with the engine. 


### Creating a basic visual novel structure
The next thing to do is to add the structure of the novel itself. We can use the DSL to simplify the process. To start with, let's add a story in the **main** function. The story will only contain a single chapter, which contains a single scene.

```kotlin
val story = story {
        this add chapter {
            this add scene {

            }
        }
    }
```

Simply build and run the application and congratulations, you have a working Ktvn visual novel!