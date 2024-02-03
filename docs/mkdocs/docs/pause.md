# Pause

## Overview
Pause is a step that prevents the story from progressing for the specified time, in milliseconds.
```kotlin
pause {
    this seconds 5
}
```
The **seconds** keyword allows the delay time to be specified, in seconds. Shorter delays can be specified in
milliseconds using the **milliseconds** keyword.
