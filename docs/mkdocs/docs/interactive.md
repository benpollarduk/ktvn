# Interactive

## Overview
Interactive is a step that adds extensibility to the game in the form of allowing other components to be called. For 
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
