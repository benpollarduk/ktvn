# Game Engine

## Overview
The **GameEngine** is a critical part of Ktvn. Essentially, the GameEngine ties together a **Game** and the environment it executes in. The GameEngine is responsible for managing game input and output. The GameEngine interface is so important because it allows a **VisualNovel** to be written largely without a target platform in mind.
Included in the Ktvn repo are a couple of examples of GameEngine implementations.

### AnsiConsoleGameEngine
In **app-ktvn-prototyoer-console** there is an example implementation of a GameEngine that targets an ANSI compatible console, **AnsiConsoleGameEngine**. Running a visual novel in a basic terminal window with limited visuals seems pretty counterproductive, but it is a clear way of demonstrating some aspects on implementing a GameEngine.

### DebugGameEngine
In **app-ktvn-prototyper-swing** there is an example implementation of a GameEngine that targets Swing. This is used in the prototyper app. It's by no means intended as an engine to use in a production game, but serves the purpose of demonstrating some of the visual features on Ktvn and is the engine that drives the prototyper app which can be used for rough prototyping of games.

## Implementing a custom GameEngine
It's important to understand the role of the GameEngine so that rich novels can be written that target any platform. In this next section I'll describe how a GameEngine can be created to target a platform.
