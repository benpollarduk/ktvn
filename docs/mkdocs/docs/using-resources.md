# Resolving Resources

## Overview
A visual novel will likely include lots of images and audio. These can be added as resources. Ktvn attempts to simplify 
the management of resources to reduce the burden on the developer as well as encouraging consistent practices and 
conventions. Ktvn does this for characters through the **CharacterResourceLookup** that is present on each 
**VisualNovel**.

## CharacterResourceLookup
A **CharacterResourceLookup** attempts to simplify the lookup process for resources related to a character. Each 
character may have different emotions and variations that need to be displayed, and at some point these will need to be 
resolved in to images for the **GameEngine** to render. The **CharacterResourceLookup** is an interface so you can 
write your own implementations if you want to use this feature, but by default a **LazyCharacterResourceLookup** class 
is provided.

## LazyCharacterResourceLookup
The **LazyCharacterResourceLookup** is the default **CharacterResourceLookup**. This provides a lookup for a character 
image in a lazy manner. Essentially it registers keys for resources as they are requested. 

When instantiating the **LazyCharacterResourceLookup** you can provide it with a default path and an extension for 
image types.

```kotlin
val characterResourceLookup: CharacterResourceLookup = LazyCharacterResourceLookup(
    "images/characters/",
    ".png"
)
```
This path should match the directory structure of your resources. For example the above path 
*"my_visual_novel/images/characters/"* would match this directory structure:

```
My_Visual_Novel
├── src
│   ├── main
│   |   ├── resources
│   |   |   ├── images
│   |   |   |   ├── characters
```

Inside that directory images for the characters can be added with names that match the pattern 
*{name}*-*{emotion}{extension}*. If the character has a variation then the pattern 
*{name}-{variation}-{emotion}{extension}* can be used. Case is not important.

For example, when adding and image for a character using PNG files where the character is called Sophie who has an 
unmodified emotion the following resource name should be used:

```
sophie-normal.png
```

The resource name for the image where she has the emotion *happy*:

```
sophie-happy.png
```

And a different variation of here looking happy, for example a version where she has aged:

```
sophie-future-happy.png
```

When the **GameEngine** needs to render the character, the correct image can be looked up through the **VisualNovel** 
instance of **CharacterResourceLookup**:

```kotlin
val imageResourcePath = visualNovel.characterResourceLookup.getKey(character)
```

This method of lazy resource name assignment means that there is little burden on the developer to manage the resources 
and there is a consistent approach applied when naming resources. However, if an explicit key needs to be used it can 
be registered with the **LazyCharacterResourceLookup**:

```kotlin
val characterResourceLookup: CharacterResourceLookup = LazyCharacterResourceLookup(
    "images/characters/",
    ".png"
).also {
    it.registerResource(character, "my-resource-name.png")
}
```
