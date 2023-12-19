package com.github.benpollarduk.ktvn.audio

/**
 * Provides a configuration for a [VolumeManager].
 */
public data class VolumeManagerConfiguration(
    public var masterVolume: Double,
    public var musicVolume: Double,
    public var soundEffectVolume: Double,
    public var voiceVolume: Double,
    public var characterVoiceVolumes: Map<String, Double>,
    public var otherVoiceVolume: Double
) {
    public companion object {
        /**
         * Get a default configuration.
         */
        public val DEFAULT: VolumeManagerConfiguration = VolumeManagerConfiguration(
            VolumeManager.NO_ATTENUATION,
            VolumeManager.NO_ATTENUATION,
            VolumeManager.NO_ATTENUATION,
            VolumeManager.NO_ATTENUATION,
            emptyMap(),
            VolumeManager.NO_ATTENUATION
        )
    }
}
