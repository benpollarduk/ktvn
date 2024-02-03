# Then

## Overview
Then is very similar to next, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
then {
    this name "Michel shows anger"
    this does {
        michel looks angry
    }
}
```
