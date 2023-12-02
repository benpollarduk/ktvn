package com.github.benpollarduk.ktvn.prototyping.swing

import org.apache.logging.log4j.kotlin.Logging
import java.io.BufferedInputStream
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent

/**
 * Provides a simple class for playing sounds.
 */
class SoundPlayer : Logging {
    private var currentSource: String? = null
    private var clip: Clip? = null

    /**
     * Play a sound from a resource [key]. Returns true if the operation was successful, else false. If [loop] is set
     * true the audio will loop indefinitely.
     */
    fun playFromResource(key: String, loop: Boolean = false): Boolean {
        return if (currentSource == key && clip != null) {
            return true
        } else {
            val stream = javaClass.classLoader.getResourceAsStream(key)
            if (stream != null) {
                currentSource = key
                val bufferedStream = AudioSystem.getAudioInputStream(BufferedInputStream(stream))
                play(bufferedStream, loop)
            } else {
                false
            }
        }
    }

    /**
     * Play a sound from a file [path]. Returns true if the operation was successful, else false. If [loop] is set true
     * the audio will loop indefinitely.
     */
    fun playFromFile(path: String, loop: Boolean = false): Boolean {
        return if (currentSource == path && clip != null) {
            return true
        } else {
            val file = File(path)
            if (file.exists()) {
                currentSource = path
                play(AudioSystem.getAudioInputStream(file), loop)
            } else {
                false
            }
        }
    }

    /**
     * Play a [audioInputStream]. Returns true if the operation was successful, else false. If [loop] is set true the
     * audio will loop indefinitely.
     */
    private fun play(audioInputStream: AudioInputStream, loop: Boolean): Boolean {
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

                if (loop) {
                    audioClip.setLoopPoints(FIRST_FRAME, LAST_FRAME)
                    audioClip.loop(Clip.LOOP_CONTINUOUSLY)
                } else {
                    audioClip.start()
                }
                true
            } else {
                logger.error("Exception caught playing stream: Clip was null.")
                false
            }
        } catch (e: javax.sound.sampled.LineUnavailableException) {
            logger.error("Exception caught playing stream: ${e.message}")
            false
        } catch (e: java.io.IOException) {
            logger.error("Exception caught playing stream: ${e.message}")
            false
        } catch (e: javax.sound.sampled.UnsupportedAudioFileException) {
            logger.error("Exception caught playing stream: ${e.message}")
            false
        }
    }

    /**
     * Stop any playing audio.
     */
    fun stop() {
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
    }
}
