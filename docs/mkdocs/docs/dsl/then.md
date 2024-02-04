# Then

## Overview
Then is very similar to next, but allows the step to be named. This is useful if the step needs to be referenced at 
some point in the story, for example if the story needs to jump to this step.

## Use
The then block can be assigned a name.

```kotlin
then {
    this name "Michel shows anger"
}
```

The code that should be executed when the step is invoked can be specified with the **does** keyword.

```kotlin
then {
    this name "Michel shows anger"
    this does {
        michel looks angry
    }
}
```
