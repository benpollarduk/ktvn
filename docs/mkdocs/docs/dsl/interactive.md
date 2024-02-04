# Interactive

## Overview
Interactive is a step that adds extensibility to the game in the form of allowing other components to be called. For 
example, some visual novels may contain mini-games. Providing the mini game implements or is wrapped by 
**InteractiveComponent** it can be invoked in a step.

## Use
The **element** keyword specifies the instance of **Interactive** that will be invoked. Any arguments can be provided 
with the **args** keyword.

```kotlin
interactive {
    this element component
    this args arrayOf("args1", "args2")
}
```

**Interactive** itself if fairly shallow and all logic should be contained within the **InteractiveComponent** that 
this step invokes.
