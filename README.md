# Introduction 
Ktvn is a framework for writing visual novels in Kotlin. It is largely based on Kotlins strong Domain Specific Language
(DSL) capabilities. The aim of Ktvn is to provide a flexible framework for writing visual novels that can be pulled in 
to other projects. Ktvn provides a quick, natural and flexible syntax that is easy to pick up and simple to maintain, 
while retaining Kotlins powerful and feature rich syntax. 

# Documentation
Please visit [https://benpollarduk.github.io/ktvn-docs/](https://benpollarduk.github.io/ktvn-docs/) to view the ktvn documentation.

# Getting Started
* Clone the repo
* Run the included terminal application
```bash
./gradlew :app-ktvn-example-console:run
```

# Hello World
```kotlin
val story = story { story ->
    story add chapter { chapter ->
        chapter name "Hello World!"
        chapter add scene { scene ->
            scene name "Introduction"
            scene background EmptyBackground()
            scene steps listOf(
                next { narrator narrates "Welcome to a Ktvn visual novel!" }
            )
        }
    }
}
```
# Structure
A Ktvn visual novel starts with a **Story**. A Story contains one or more **Chapters**. Each Chapter contains one or 
more **Scenes**. Each Scene contains one or more **Steps**. There are several types of Step, and Step is extensisible 
so that the DSL can be customised.   
  
Simple classes and DSL exist to support characters, narration, choices, flags, emotions and positioning of characters. 
Interfaces for listeners are provided so that UIs can respond events, and interfaces for acknowledgments are included 
to aid with flow control.

# Core DSL
The Ktvn DSL is simple but powerful. Each step in a story has access to the parent story's flags, which allows variables 
such as user responses to be captured and made accessible to other steps. **Ktvn is in the very early stages of 
development and the DSL in particular may change.**

### next ###
next is a simple step to allow one or more actions to take place. For example, the narrator can say a line:
```kotlin
next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
```
Or a character may speak:
```kotlin
next { morgana says "The two of us are cursed to spend eternity in this mansion." }
```
Characters can show emotions:
```kotlin
next { michel looks concerned }
```
Or change position on the screen:
```kotlin
next { layout moveRight morgana }
```

### then ###
then is very similar to next, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
then {
    it name "Michel shows anger"
    it does {
        michel looks angry
    }
}
```

### choice ###
choice allows the user to present a question to the user and receive an answer. A character or the narrator can ask a 
multiple choice question:
```kotlin
choice {
    morgana asks question { question ->
        question line "Why we do, don't we dear?"
        question option answer { answer ->
            answer line "Of course."
            answer does {
                it.setFlag("Michel likes Morgana")
            }
        }
        question option answer { answer ->
            answer line "I hate you!"
            answer does {
                it.setFlag("Michel hates Morgana")
            }
        }
    }
}
```
Each choice can have multiple options. Each option has an optional script specified by the **does** keyword. In this 
case a flag is set to indicate that the user picked an option.

### decision ###
decision is very similar to choice, but allows the step to be named. This is useful if the story needs to jump to this step.
```kotlin
decision { decision ->
    decision name "Michel's feeling towards Morgana"
    decision does {
        morgana asks question { question ->
            question line "Why we do, don't we dear?"
            question option answer { answer ->
                answer line "Of course."
                answer does {
                    it.setFlag("Michel likes Morgana")
                }
            }
            question option answer { answer ->
                answer line "I hate you!"
                answer does {
                    it.setFlag("Michel hates Morgana")
                }
            }
        }
    }
}
```

### conditional ###
conditional allows a step to only be invoked if a specified condition is met.
```kotlin
conditional {
    it condition "Michel likes Morgana"
    it does {
        morgana looks amused
        morgana says "Fool, I despise you!"
    }
    it returns StepResult.Continue
}
```
The **condition** keyword specifies the flag. If that flag is set to true then the script specified by the **does** 
keyword will be executed. Lastly the **returns** keyword specifies the result of the step so that the story can 
continue, branch or end as required.

### end ###
end is a simple step that signifies that an ending has been reached.
```kotlin
end {
    it number 0
}
```
The ending that was reached can be specified with the **number** keyword.

For further examples please see the ktvn-example directory in the repo.

# For Open Questions
Visit https://github.com/benpollarduk/ktvn/issues
