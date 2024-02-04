# Conditional

## Overview
Conditional allows a step to only be invoked if a specified condition is met.

## Use
The **condition** must be specified. If this case the **flag** "Michel likes Morgana" must be present and set true for 
**does** block to be executed when the step is invoked. The **returns** keyword specifies how the scene should continue 
when this step completes.

```kotlin
conditional {
    this condition "Michel likes Morgana"
    this does {
        morgana looks amused
        morgana says "Fool, I despise you!"
    }
    this returns Continue
}
```
Conditional can be though of as a simple *if* statement.
