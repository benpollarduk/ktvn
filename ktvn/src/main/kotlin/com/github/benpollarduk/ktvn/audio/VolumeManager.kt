package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides a class for managing volumes.
 */
@Suppress("TooManyFunctions")
public class VolumeManager {
    private var masterVolume: Double = NO_ATTENUATION
    private var musicVolume: Double = NO_ATTENUATION
    private var soundEffectVolume: Double = NO_ATTENUATION
    private var voiceVolume: Double = NO_ATTENUATION
    private var otherVoiceVolume: Double = NO_ATTENUATION
    private var characterVoiceVolumes: MutableMap<String, Double> = mutableMapOf()

    /**
     * Set the master volume to the specified [volume]. The [volume] is specified using a normalised value between 0.0
     * (silence) and 1.0 (no attenuation).
     */
    public fun setMasterVolume(volume: Double) {
        masterVolume = maxOf(FULL_ATTENUATION, minOf(NO_ATTENUATION, volume))
    }

    /**
     * Get the master volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun getMasterVolume(): Double {
        return masterVolume
    }

    /**
     * Set the music volume to the specified [volume]. The [volume] is specified using a normalised value between 0.0
     * (silence) and 1.0 (no attenuation).
     */
    public fun setMusicVolume(volume: Double) {
        musicVolume = maxOf(FULL_ATTENUATION, minOf(NO_ATTENUATION, volume))
    }

    /**
     * Get the music volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun getMusicVolume(): Double {
        return musicVolume
    }

    /**
     * Set the sound effect volume to the specified [volume]. The [volume] is specified using a normalised value
     * between 0.0 (silence) and 1.0 (no attenuation).
     */
    public fun setSoundEffectVolume(volume: Double) {
        soundEffectVolume = maxOf(FULL_ATTENUATION, minOf(NO_ATTENUATION, volume))
    }

    /**
     * Get the sound effect volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun getSoundEffectVolume(): Double {
        return soundEffectVolume
    }

    /**
     * Set the global voice volume to the specified [volume]. The [volume] is specified using a normalised value
     * between 0.0 (silence) and 1.0 (no attenuation).
     */
    public fun setVoiceVolume(volume: Double) {
        voiceVolume = maxOf(FULL_ATTENUATION, minOf(NO_ATTENUATION, volume))
    }

    /**
     * Get the global voice volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun getVoiceVolume(): Double {
        return voiceVolume
    }

    /**
     * Set a [character] voice volume to the specified [volume]. The [volume] is specified using a normalised value
     * between 0.0 (silence) and 1.0 (no attenuation).
     */
    public fun setCharacterVoiceVolume(character: Character, volume: Double) {
        characterVoiceVolumes[character.name] = volume
    }

    /**
     * Get the [character] voice volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun getCharacterVoiceVolume(character: Character): Double {
        return characterVoiceVolumes[character.name] ?: otherVoiceVolume
    }

    /**
     * Calculate music volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun calculateMusicVolume(): Double {
        return masterVolume * musicVolume
    }

    /**
     * Calculate sound effect volume. The volume is returned using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation).
     */
    public fun calculateSoundEffectVolume(): Double {
        return masterVolume * soundEffectVolume
    }

    /**
     * Calculate voice volume for a specified [character]. The volume is returned using a normalised value between 0.0
     * (silence) and 1.0 (no attenuation).
     */
    public fun calculateVoiceVolume(character: Character?): Double {
        val characterVolume = if (character != null) getCharacterVoiceVolume(character) else otherVoiceVolume
        return masterVolume * voiceVolume * characterVolume
    }

    /**
     * Reset all volumes.
     */
    public fun reset() {
        masterVolume = NO_ATTENUATION
        musicVolume = NO_ATTENUATION
        soundEffectVolume = NO_ATTENUATION
        voiceVolume = NO_ATTENUATION
        otherVoiceVolume = NO_ATTENUATION
        characterVoiceVolumes.clear()
    }

    /**
     * Configure this volume manager from a [configuration].
     */
    public fun configure(configuration: VolumeManagerConfiguration) {
        masterVolume = configuration.masterVolume
        musicVolume = configuration.musicVolume
        soundEffectVolume = configuration.soundEffectVolume
        voiceVolume = configuration.voiceVolume
        otherVoiceVolume = configuration.otherVoiceVolume
        characterVoiceVolumes = configuration.characterVoiceVolumes.toMutableMap()
    }

    /**
     * Create a [VolumeManagerConfiguration] from this volume manager.
     */
    public fun toConfiguration() : VolumeManagerConfiguration {
        return VolumeManagerConfiguration(
            masterVolume,
            musicVolume,
            soundEffectVolume,
            voiceVolume,
            characterVoiceVolumes,
            otherVoiceVolume
        )
    }

    public companion object {
        /**
         * Get the value for no attenuation.
         */
        private const val NO_ATTENUATION: Double = 1.0

        /**
         * Get the value for full attenuation.
         */
        private const val FULL_ATTENUATION: Double = 0.0
    }
}
