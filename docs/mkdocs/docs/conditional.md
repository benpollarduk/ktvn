# Conditional

## Overview
Conditional allows a step to only be invoked if a specified condition is met.
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
The **condition** keyword specifies the flag. If that flag is set to true then the script specified by the **does** 
keyword will be executed. Lastly the **returns** keyword specifies the result of the step so that the story can 
continue, branch or end as required.
