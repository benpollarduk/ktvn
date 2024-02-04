# Structure
A Ktvn visual novel starts with a **Story**. A Story contains one or more **Chapters**. Each Chapter contains one or
more **Scenes**. Each Scene contains one or more **Steps**. There are several types of Step, and Step is extensible
so that the DSL can be customised.

```
Story
├── Chapter
│   ├── Scene
│   |   ├── Step
│   |   ├── Step
│   |   ├── Step
|   ├── Scene
│   |   ├── Step
│   |   ├── Step
├── Chapter
│   ├── Scene
│   |   ├── Step
|   ├── Scene
│   |   ├── Step
``` 
This simple structure can be used to develop entire stories.