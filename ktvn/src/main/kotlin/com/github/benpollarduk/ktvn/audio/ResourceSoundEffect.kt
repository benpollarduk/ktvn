package com.github.benpollarduk.ktvn.audio

/**
 * A sound effect from a resource, identified by a [key].
 */
public data class ResourceSoundEffect(public var key: String) : SoundEffect {
    public companion object {
        /**
         * Create a sound effect from a resource with a [key].
         */
        public infix fun sfxFromResource(key: String): ResourceSoundEffect {
            return ResourceSoundEffect(key)
        }
    }
}
