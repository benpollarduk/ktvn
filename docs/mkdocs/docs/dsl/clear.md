# Clear

## Overview
Clear is a step that signals that the current scene should be cleared. The engine is responsible for actioning this in 
a way that makes sense for the executing game.

## Use
Clear has no parameters and is presented as an empty block.

```kotlin
clear { }
```

Clear name be given a name if desired, which can be useful if it needs to be referenced from within a story.

```kotlin
clear {
    this name "Clear step"
}
```

Suggested use cases are for clearing the text from a narrative scene, or removing all dialog from a dialog scene.
