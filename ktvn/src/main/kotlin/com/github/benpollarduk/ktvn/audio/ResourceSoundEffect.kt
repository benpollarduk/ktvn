package com.github.benpollarduk.ktvn.audio

/**
 * A resource sound effect, identified by a [key].
 */
public data class ResourceSoundEffect(public var key: String) : SoundEffect {
    public companion object {
        /**
         * Create a resource sound effect with a [key].
         */
        public infix fun sfxFromResource(key: String): ResourceSoundEffect {
            return ResourceSoundEffect(key)
        }
    }
}
