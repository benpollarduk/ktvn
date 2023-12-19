package com.github.benpollarduk.ktvn.audio

import java.io.BufferedInputStream
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl
import javax.sound.sampled.LineEvent
import kotlin.math.log10

/**
 * Provides a simple class for playing sounds.
 */
public class SoundPlayer {
    private var currentSource: String? = null
    private var clip: Clip? = null

    /**
     * Play a sound from a resource [key]. If [volume] is specified then the signal can be attenuated. The [volume] is
     * specified using a normalised value between 0.0 (silence) and 1.0 (no attenuation), but is set to 1.0 as default.
     * If [loop] is set true the audio will loop indefinitely. Returns a result detailing if the operation was
     * successful.
     */
    public fun playFromResource(
        key: String,
        volume: Double = NO_ATTENUATION,
        loop: Boolean = false
    ): SoundPlaybackResult {
        return if (currentSource == key && clip != null) {
            return SoundPlaybackResult.SUCCESS
        } else {
            val stream = javaClass.classLoader.getResourceAsStream(key)
            if (stream != null) {
                currentSource = key
                val bufferedStream = AudioSystem.getAudioInputStream(BufferedInputStream(stream))
                play(bufferedStream, volume, loop)
            } else {
                SoundPlaybackResult(false, "Stream was null.")
            }
        }
    }

    /**
     * Play a sound from a file [path]. The [volume] is specified using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation), but is set to 1.0 as default. If [loop] is set true the audio will loop indefinitely. Returns a
     * result detailing if the operation was successful.
     */
    public fun playFromFile(
        path: String,
        volume: Double = NO_ATTENUATION,
        loop: Boolean = false
    ): SoundPlaybackResult {
        return if (currentSource == path && clip != null) {
            return SoundPlaybackResult.SUCCESS
        } else {
            val file = File(path)
            if (file.exists()) {
                currentSource = path
                play(AudioSystem.getAudioInputStream(file), volume, loop)
            } else {
                SoundPlaybackResult(false, "File does not exist.")
            }
        }
    }

    /**
     * Play a [audioInputStream]. The [volume] is specified using a normalised value between 0.0 (silence) and 1.0
     * (no attenuation). If [loop] is set true the audio will loop indefinitely. Returns a result detailing if the
     * operation was successful.
     */
    private fun play(audioInputStream: AudioInputStream, volume: Double, loop: Boolean): SoundPlaybackResult {
        return try {
            val baseFormat = audioInputStream.format

            val decodedFormat = AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.sampleRate,
                DEFAULT_SAMPLE_SIZE_IN_BITS,
                baseFormat.channels,
                baseFormat.channels * STEREO_CHANNELS,
                baseFormat.sampleRate,
                false
            )

            val decodedInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream)
            val audioClip = AudioSystem.getClip()

            if (audioClip != null) {
                audioClip.addLineListener {
                    if (it.type == LineEvent.Type.STOP) {
                        clip?.close()
                        clip = null
                        currentSource = null
                    }
                }

                clip = audioClip
                audioClip.open(decodedInputStream)

                // set the volume, if available
                if (audioClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    val gainControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
                    val volumeInDb = (20.0f * log10(volume)).toFloat()
                    gainControl.value = volumeInDb
                }

                if (loop) {
                    audioClip.setLoopPoints(FIRST_FRAME, LAST_FRAME)
                    audioClip.loop(Clip.LOOP_CONTINUOUSLY)
                } else {
                    audioClip.start()
                }
                SoundPlaybackResult.SUCCESS
            } else {
                SoundPlaybackResult(false, "Exception caught playing stream: Clip was null.")
            }
        } catch (e: javax.sound.sampled.LineUnavailableException) {
            SoundPlaybackResult(false, "Exception caught playing stream: ${e.message}")
        } catch (e: java.io.IOException) {
            SoundPlaybackResult(false, "Exception caught playing stream: ${e.message}")
        } catch (e: javax.sound.sampled.UnsupportedAudioFileException) {
            SoundPlaybackResult(false, "Exception caught playing stream: ${e.message}")
        }
    }

    /**
     * Stop any playing audio.
     */
    public fun stop() {
        clip?.stop()
    }

    private companion object {
        /**
         * Get the default sample size, in bits.
         */
        private const val DEFAULT_SAMPLE_SIZE_IN_BITS: Int = 16

        /**
         * Get the number of stereo channels.
         */
        private const val STEREO_CHANNELS: Int = 2

        /**
         * Get the value for the first frame.
         */
        private const val FIRST_FRAME: Int = 0

        /**
         * Get the value for the last frame.
         */
        private const val LAST_FRAME: Int = -1

        /**
         * Get the value for no attenuation
         */
        private const val NO_ATTENUATION: Double = 1.0
    }
}
