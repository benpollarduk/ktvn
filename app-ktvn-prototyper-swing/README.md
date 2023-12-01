# Introduction 
ktvn-app-prototyper-swing provides an application for prototyping and testing and Ktvn visual novels. It isn't 
pretty but does allow for basic testing and will be continued to be developed and improved as Ktvn grows. Swing was chosen as it 
allows testing across multiple OS without the need for platform specific solutions.

# Getting Started
* Clone the repo.
* Build the prototyping application.
```bash
./gradlew :app-ktvn-prototyper-swing:build
```
* Run the prototyping application.
```bash
cd app-ktvn-prototyper-swing/build/libs
java -jar app-ktvn-prototyper-swing-all.jar
```
* Run an example visual novel
  * Open Game > Examples and select one of the example visual novels.
  * The Fate of Morgana:
    * Purposely misses audio and visual resources.
    * Demonstrates multiple chapters and scenes.
    * Demonstrates flags and decisions.
  * Shuttle Launch:
    * Minimal content.
    * Demonstrates audio and visual resources.
   
# What ktvn-app-prototyper-swing does #
ktvn-app-prototyper-swing supports:
* Loading and execution of bundled example visual novels
* Dynamic loading of VisualNovel instances in .jar files
* Sequenced rendering of text
* Rendering of backgrounds an characters
* Simple audio playback for tracks and sfx
* Progression controls and ability to jump to specified chapter, scene, step
* Simple log viewer
* Simple resource viewer
* Simple flag viewer
   
# What ktvn-app-prototyper-swing doesn't do #
ktvn-app-prototyper-swing doesn't currently support:
* Character animations
* Animations when a character moves
* Scene transitions
* Distinct UI for narrative scenes

# For Open Questions
Visit https://github.com/benpollarduk/ktvn/issues
