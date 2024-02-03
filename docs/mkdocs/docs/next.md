# Next

## Overview
Next is a simple step to allow one or more actions to take place. For example, the narrator can say a line:
```kotlin
next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
```
Or a character may speak:
```kotlin
next { morgana says "The two of us are cursed to spend eternity in this mansion." }
```
Or a character may think:
```kotlin
next { morgana thinks "I could never tell Michel I don't actually think he is a fool." }
```
Characters can show emotions:
```kotlin
next { michel looks concerned }
```
Characters can be animated:
```kotlin
next { michel begins shaking }
```
Change position on the screen:
```kotlin
next { layout moveRight morgana }
```
Or play a sound effect:
```kotlin
next { audio sfx sfxFromResource("crash") }
```
