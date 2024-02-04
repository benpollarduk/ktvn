# Decision

## Overview
Decision is very similar to **Choice**, but allows the step to be named. This is useful if the story needs to jump to 
this step, or if the step should be referenced from within the story for any other reason.

## Use
The **name** keyword allows the **decision** step to be assigned a name. The **does** keyword specifies the code that 
will be executed when the step is invoked.

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

Decision may be a more appropriate step to use than **Choice** in cases where the step itself should be referenced.
