package com.github.benpollarduk.ktvn.prototyping.swing

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.ChapterRestorePoint
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.io.restore.SceneRestorePoint
import com.github.benpollarduk.ktvn.io.restore.StoryRestorePoint
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.VisualNovel
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JFrameAnswerPicker
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JMenuBarMainMenu
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JPanelFlagViewer
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JPanelProgressionControl
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JPanelResourceTracker
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JPanelStatus
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JPanelVisualScene
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JTextAreaSequencedTextArea
import com.github.benpollarduk.ktvn.prototyping.swing.ui.JTextPaneEventTerminal
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.SwingUtilities

@Suppress("MagicNumber")
class App : JFrame(FALLBACK_TITLE) {
    /**
     * Get or set the visual novel.
     */
    var visualNovel: VisualNovel? = null

    /**
     * Get or set the game.
     */
    var game: Game? = null

    /**
     * Get or set the game save.
     */
    var gameSave: GameSave = GameSave.EMPTY

    /**
     * Get or set the step data path.
     */
    var stepDataPath: String = ""

    private val eventTerminal = JTextPaneEventTerminal().apply {
        preferredSize = Dimension(0, 80)
        minimumSize = preferredSize
    }
    private val sequencedTextArea = JTextAreaSequencedTextArea().apply {
        preferredSize = Dimension(0, 80)
        minimumSize = preferredSize
    }
    private val visualScene = JPanelVisualScene()
    private val answerPicker = JFrameAnswerPicker()
    private val status = JPanelStatus()
    private val resourceTracker = JPanelResourceTracker().apply {
        preferredSize = Dimension(350, 0)
        minimumSize = preferredSize
    }
    private val flagViewer = JPanelFlagViewer().apply {
        preferredSize = Dimension(350, 0)
        minimumSize = preferredSize
    }
    private val engine: com.github.benpollarduk.ktvn.prototyping.swing.DebugGameEngine =
        com.github.benpollarduk.ktvn.prototyping.swing.DebugGameEngine(
            eventTerminal,
            visualScene,
            sequencedTextArea,
            answerPicker,
            status,
            resourceTracker,
            flagViewer
        ) {
            progressionControl.allowAcknowledge(it)
        }
    private val mainMenu = JMenuBarMainMenu(this, eventTerminal, engine)
    private val progressionControl = JPanelProgressionControl(engine) { chapter, scene, step ->
        eventTerminal.println(Severity.INFO, "Jumping to $chapter.$scene.$step...")
        val visualNovel = visualNovel
        if (visualNovel != null) {
            val storyRestorePoint = StoryRestorePoint(
                ChapterRestorePoint(
                    SceneRestorePoint(
                        emptyList(),
                        step
                    ),
                    scene
                ),
                chapter
            )
            val tracker = visualNovel.configuration.stepTracker
            if (stepDataPath != "") {
                mainMenu.saveStepDataFile(tracker, stepDataPath)
            }
            beginGame(
                visualNovel,
                stepDataPath,
                gameSave,
                RestorePoint("", Flags().toMap(), storyRestorePoint)
            )
        }
    }

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(1650, 1000)
        layout = BorderLayout()

        val mainFrame = JPanel()
        mainFrame.layout = BorderLayout()

        val toolsPanel = JPanel().apply {
            layout = GridLayout(3, 1)
            add(sequencedTextArea)
            add(resourceTracker)
            add(flagViewer)
        }

        val backgroundAndControlsSplitPane = JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            visualScene,
            toolsPanel
        ).also {
            it.resizeWeight = 0.7
        }

        val centralSplitPane = JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            backgroundAndControlsSplitPane,
            eventTerminal
        ).also {
            it.resizeWeight = 0.8
        }

        val centralAndProgressionJPanel = JPanel()
        centralAndProgressionJPanel.layout = BorderLayout()
        centralAndProgressionJPanel.add(progressionControl, BorderLayout.NORTH)
        centralAndProgressionJPanel.add(centralSplitPane, BorderLayout.CENTER)

        jMenuBar = mainMenu
        mainFrame.add(centralAndProgressionJPanel, BorderLayout.CENTER)
        mainFrame.add(status, BorderLayout.SOUTH)

        add(mainFrame)

        isVisible = true
    }

    fun applyRestorePoint(restorePoint: RestorePoint) {
        val vn = visualNovel ?: return
        beginGame(vn, stepDataPath, gameSave, restorePoint)
    }

    fun applyGameSave(gameSave: GameSave) {
        this.gameSave = gameSave
        val vn = visualNovel ?: return
        val restorePoint = game?.getRestorePoint("Auto") ?: RestorePoint.EMPTY
        beginGame(vn, stepDataPath, gameSave, restorePoint)
    }

    fun applyStepData(path: String) {
        stepDataPath = path
        val vn = visualNovel ?: return
        val restorePoint = game?.getRestorePoint("Auto") ?: RestorePoint.EMPTY
        beginGame(vn, path, gameSave, restorePoint)
    }

    fun beginGame(
        visualNovel: VisualNovel,
        stepDataPath: String = "",
        gameSave: GameSave = GameSave.EMPTY,
        restorePoint: RestorePoint = RestorePoint.EMPTY
    ) {
        unloadGame()

        if (visualNovel.desiredResolution != Resolution.NOT_SPECIFIED) {
            visualScene.desiredResolution = visualNovel.desiredResolution
        } else {
            visualScene.desiredResolution = Resolution(1024, 768)
        }

        this.visualNovel = visualNovel
        engine.setupForVisualNovel(visualNovel)

        if (stepDataPath != "") {
            val loadResult = visualNovel.configuration.stepTracker.restore(stepDataPath)

            if (!loadResult.result) {
                JOptionPane.showMessageDialog(
                    this,
                    loadResult.message,
                    "Load Step Data",
                    JOptionPane.ERROR_MESSAGE
                )
                return
            }
        }

        title = visualNovel.story.name
        eventTerminal.println(Severity.INFO, "Beginning async execution of $visualNovel...")
        visualNovel.apply {
            configuration.engine = engine
        }

        val newGame = Game(visualNovel, gameSave, restorePoint)
        game = newGame

        GameExecutor.executeAysnc(newGame) {
            this.gameSave = it.gameSave
        }
    }

    fun unloadGame() {
        eventTerminal.println(Severity.INFO, "Ending execution of any executing games...")
        engine.setupForNoVisualNovel()
        GameExecutor.cancel()
        visualNovel = null
        engine.textController.stop()
        visualScene.reset()
        progressionControl.reset()
        sequencedTextArea.reset()
        status.reset()
        resourceTracker.reset()
        flagViewer.reset()
        title = FALLBACK_TITLE
    }

    companion object {
        /**
         * Default/fallback title.
         */
        private const val FALLBACK_TITLE: String = "Ktvn Prototyper"

        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {
                com.github.benpollarduk.ktvn.prototyping.swing.App()
            }
        }
    }
}
