package com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect.Companion.soundEffectFromResource
import com.github.benpollarduk.ktvn.audio.ResourceTrack.Companion.trackFromResource
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.audio.Track
import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground.Companion.backgroundFromResource
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.characters.animations.Laugh
import com.github.benpollarduk.ktvn.characters.animations.Shake
import com.github.benpollarduk.ktvn.layout.transitions.FadeIn
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.layout.transitions.Slide
import com.github.benpollarduk.ktvn.logic.configuration.DynamicGameConfiguration
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import java.awt.Color

/**
 * An object that acts as a store for the games assets.
 */
@Suppress("MagicNumber")
public object AssetStore {
    /**
     * Get the scene transition duration, in milliseconds.
     */
    private const val SCENE_TRANSITION_DURATION_IN_MS: Long = 350

    /**
     * Get the slide transition duration, in milliseconds.
     */
    private const val LAYOUT_SLIDE_DURATION_IN_MS: Long = 250

    /**
     * Get the fade in transition duration, in milliseconds.
     */
    private const val LAYOUT_FADE_IN_DURATION_IN_MS: Long = 250

    /**
     * Get the game configuration.
     */
    internal val configuration: DynamicGameConfiguration = DynamicGameConfiguration()

    /**
     * The narrator - responsible for narrating the story.
     */
    public val narrator: Narrator = Narrator(configuration.gameAdapter.narratorAdapter)

    /**
     * The audio manager, responsible for all audio.
     */
    public val audio: AudioManager = AudioManager(configuration.gameAdapter.audioAdapter)

    /**
     * Toki, a cosmonaut.
     */
    public val toki: Character = Character("Toki", configuration.gameAdapter.characterAdapter)

    /**
     * Memory of Toki, a cosmonaut.
     */
    public val tokiMemory: Character = Character("Toki", configuration.gameAdapter.characterAdapter, "Memory")

    /**
     * Sophie, a scholar.
     */
    public val sophie: Character = Character("Sophie", configuration.gameAdapter.characterAdapter)

    /**
     * Get the shuttle daytime background.
     */
    public val shuttleDay: Background = backgroundFromResource("shuttleLaunch/images/backgrounds/shuttle-day.png")

    /**
     * Get the shuttle memory background.
     */
    public val shuttleMemory: Background =
            backgroundFromResource("shuttleLaunch/images/backgrounds/shuttle-memory.png")

    /**
     * Get the track for the shuttle day scene.
     */
    public val shuttleDayMusic: Track = trackFromResource("shuttleLaunch/audio/shuttle-day.wav")

    /**
     * Get the 'woosh' sound effect.
     */
    public val sfxWoosh: SoundEffect = soundEffectFromResource("shuttleLaunch/audio/woosh.wav")

    /**
     * Get the fade in scene transition.
     */
    public val sceneFadeIn: SceneTransition =
        com.github.benpollarduk.ktvn.structure.transitions.FadeIn(Color.BLACK, SCENE_TRANSITION_DURATION_IN_MS)

    /**
     * Get the fade out scene transition.
     */
    public val sceneFadeOut: SceneTransition =
        com.github.benpollarduk.ktvn.structure.transitions.FadeOut(Color.BLACK, SCENE_TRANSITION_DURATION_IN_MS)

    /**
     * Get the slide layout transition.
     */
    public val layoutSlide: LayoutTransition = Slide(LAYOUT_SLIDE_DURATION_IN_MS)

    /**
     * Get the fade in layout transition.
     */
    public val layoutFadeIn: LayoutTransition = FadeIn(LAYOUT_FADE_IN_DURATION_IN_MS)

    /**
     * A laughing animation.
     */
    public val laughing: Animation = Laugh(0.1, 5, 40)

    /**
     * A shaking animation.
     */
    public val shaking: Animation = Shake(0.2, 0.0, 10, 20)
}
