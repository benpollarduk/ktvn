package com.github.benpollarduk.ktvn.swing

import com.github.benpollarduk.ktvn.example.TheFateOfMorgana
import com.github.benpollarduk.ktvn.io.discovery.CatalogEntry
import com.github.benpollarduk.ktvn.io.discovery.StoryCatalogResolver
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.StoryTemplate
import com.github.benpollarduk.ktvn.swing.ui.JLabelBackground
import com.github.benpollarduk.ktvn.swing.ui.JPanelProgressionControl
import com.github.benpollarduk.ktvn.swing.ui.JTextAreaSequencedTextArea
import com.github.benpollarduk.ktvn.swing.ui.JTextPaneEventTerminal
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import java.io.File
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter

@Suppress("MagicNumber")
class App : JFrame("Ktvn Debugger") {
    private val settings: ApplicationSettings = ApplicationSettings()
    private val sequencedTextArea = JTextAreaSequencedTextArea()
    private val background = JLabelBackground(settings.resolutionWidth, settings.resolutionHeight).also {
        it.horizontalAlignment = JLabel.CENTER
        it.verticalAlignment = JLabel.CENTER
    }
    private val eventTerminal = JTextPaneEventTerminal().also {
        it.preferredSize = Dimension(0, 80)
        it.minimumSize = it.preferredSize
    }
    private val engine: DebugGameEngine = DebugGameEngine(eventTerminal, background, sequencedTextArea)
    private val progressionControl = JPanelProgressionControl(engine)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(1024, 768)
        layout = BorderLayout()

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

        val centralAndProgressionJPanel = JPanel().also {
            it.layout = BorderLayout()
            it.add(progressionControl, BorderLayout.NORTH)
            it.add(centralSplitPane, BorderLayout.CENTER)
        }

        add(createMenu(), BorderLayout.NORTH)
        add(centralAndProgressionJPanel, BorderLayout.CENTER)

        isVisible = true
    }

    private fun createMenu(): JMenuBar {
        val menu = JMenuBar()
        val gameMenuItem = JMenu("Game")
        val importJarMenuItem = JMenuItem("Load StoryTemplate from .jar...")
        val examplesMenuItem = JMenu("Examples")
        val ktvnDemoMenuItem = JMenuItem("ktvn-demo")

        importJarMenuItem.addActionListener {
            val fileChooser = JFileChooser()
            val jarFileFilter = FileNameExtensionFilter("JAR Files", "jar")
            fileChooser.fileFilter = jarFileFilter
            val returnValue = fileChooser.showOpenDialog(this@App)
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                val file = fileChooser.selectedFile
                loadStoryTemplateFromFile(file)
                println(file.name)
            }
        }

        ktvnDemoMenuItem.addActionListener {
            beginStory(TheFateOfMorgana())
        }

        examplesMenuItem.add(ktvnDemoMenuItem)
        gameMenuItem.add(examplesMenuItem)
        gameMenuItem.add(importJarMenuItem)
        menu.add(gameMenuItem)
        return menu
    }

    private fun loadStoryTemplateFromFile(file: File) {
        val catalogEntries = StoryCatalogResolver.resolveCatalogFromJar(file)
        val storyTemplates = catalogEntries.get()
        if (storyTemplates.size == 1) {
            beginStory(storyTemplates.first().template)
        } else if (storyTemplates.size > 1) {
            handleMultipleStoryTemplatesFoundInJar(storyTemplates)
        } else {
            eventTerminal.println(Severity.ERROR, "No stories found in ${file.name}.")
            JOptionPane.showMessageDialog(
                this,
                "No stories were found.",
                "No stories found in ${file.name}",
                JOptionPane.INFORMATION_MESSAGE
            )
        }
    }

    private fun handleMultipleStoryTemplatesFoundInJar(storyTemplates: List<CatalogEntry<StoryTemplate>>) {
        val dialog = JDialog(this, "Select Story Template", true)
        dialog.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        dialog.layout = FlowLayout()

        storyTemplates.forEach { story ->
            val button = JButton(story.name)
            button.addActionListener {
                beginStory(story.template)
                dialog.dispose()
            }
            dialog.add(button)
        }

        dialog.pack()
        dialog.setLocationRelativeTo(this)
        dialog.isVisible = true
    }

    private fun beginStory(
        storyTemplate: StoryTemplate,
        gameSave: GameSave = GameSave.empty,
        restorePoint: RestorePoint = RestorePoint.empty
    ) {
        eventTerminal.println(Severity.INFO, "Ending execution of any executing games...")
        GameExecutor.cancel()
        eventTerminal.println(Severity.INFO, "Beginning async execution of $storyTemplate...")
        storyTemplate.also {
            it.configuration?.engine = engine
        }

        val game = Game(storyTemplate, gameSave, restorePoint)
        GameExecutor.executeAysnc(game) {
            // handle end
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
