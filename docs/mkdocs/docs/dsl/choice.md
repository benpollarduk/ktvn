# Choice

## Overview
Choice allows the user to present a question to the user and receive an answer.

## Use
A character or the narrator can ask a multiple choice question.

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
between steps. Each flag has a string key and a boolean value. If a flag does not exist when it is read then false will 
be returned as default. In this case a flag is set to register the option that the user picked.

