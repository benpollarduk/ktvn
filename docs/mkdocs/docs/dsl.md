# DSL

## Overview
At the core of Ktvn lies the domain specifc language (DSL) that aims to simplify the process of developing a visual novel.

### next
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
continue, branch or end as required.### next ###
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
pause is a step that prevents the story from progressing for the specified time, in milliseconds.
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
