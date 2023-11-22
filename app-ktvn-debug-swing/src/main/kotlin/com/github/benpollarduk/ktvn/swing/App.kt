package com.github.benpollarduk.ktvn.swing

import com.github.benpollarduk.ktvn.example.TheFateOfMorgana
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.StandardGameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.swing.ui.JLabelBackground
import com.github.benpollarduk.ktvn.swing.ui.JTextAreaSequencedTextArea
import com.github.benpollarduk.ktvn.swing.ui.JTextPaneEventTerminal
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JSplitPane
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter
import org.apache.logging.log4j.kotlin.Logging

@Suppress("MagicNumber")
class App : JFrame("Ktvn Debugger"), Logging {
    private val sequencedTextArea = JTextAreaSequencedTextArea()
    private val background = JLabelBackground()
    private val eventTerminal = JTextPaneEventTerminal().also {
        it.preferredSize = Dimension(0, 80)
        it.minimumSize = it.preferredSize
    }
    private val engine: GameEngine = DebugGameEngine(eventTerminal, background, sequencedTextArea)
    private val configuration: GameConfiguration = StandardGameConfiguration(engine)

    init {
        // set up main frame
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setSize(1024, 768)

        val imageAndStorySplitPane = JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            background,
            sequencedTextArea
        ).also {
            it.resizeWeight = 0.5
        }

        val centralSplitPane = JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            imageAndStorySplitPane,
            eventTerminal
        ).also {
            it.resizeWeight = 0.8
        }

        this.layout = BorderLayout()

        // add panels to this frame
        this.add(createMenu(), BorderLayout.NORTH)
        this.add(centralSplitPane, BorderLayout.CENTER)

        val story = TheFateOfMorgana().instantiate()
        beginStory(story)

        this.isVisible = true
    }

    private fun createMenu(): JMenuBar {
        val menu = JMenuBar()
        val gameMenuItem = JMenu("File")
        val importJarMenuItem = JMenuItem("Load .jar...")
        val examplesMenuItem = JMenu("Examples")
        val ktvnDemoMenuItem = JMenuItem("ktvn-demo")

        importJarMenuItem.addActionListener {
            val fileChooser = JFileChooser()
            val jarFileFilter = FileNameExtensionFilter("JAR Files", "jar")
            fileChooser.fileFilter = jarFileFilter
            val returnValue = fileChooser.showOpenDialog(this@App)
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                val file = fileChooser.selectedFile
                // loadGameFromFile(file)
                println(file.name)
            }
        }

        ktvnDemoMenuItem.addActionListener {
            // beginStory(story)
        }

        examplesMenuItem.add(ktvnDemoMenuItem)
        gameMenuItem.add(examplesMenuItem)
        gameMenuItem.add(importJarMenuItem)
        menu.add(gameMenuItem)
        return menu
    }

    private fun beginStory(story: Story) {
        GameExecutor.cancel()

        val game = Game(story, configuration, GameSave.empty, RestorePoint.empty)
        GameExecutor.executeAysnc(game) {
            // any post run
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {
                App()
            }
        }
    }
}
