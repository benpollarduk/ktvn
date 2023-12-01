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

# For Open Questions
Visit https://github.com/benpollarduk/ktvn/issues