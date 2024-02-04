# Pause

## Overview
Pause is a step that prevents the story from progressing for the specified time.

## Use
The duration of the pause can be specified in seconds.

```kotlin
pause {
    this seconds 5
}
```

For smaller pauses, the duration can be specified in milliseconds.

```kotlin
pause {
    this milliseconds 150
}
```

The pause will be at least the specified duration, but it cannot be guaranteed that it won't be longer so should not be 
used for synchronisation purposes.