# Decision

## Overview
Decision is very similar to choice, but allows the step to be named. This is useful if the story needs to jump to this step.
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

