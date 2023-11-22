package com.github.benpollarduk.ktvn.swing

import com.github.benpollarduk.ktvn.swing.ui.EventTerminalJTextArea
import org.apache.logging.log4j.kotlin.Logging
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JSplitPane
import javax.swing.JTextArea
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter

@Suppress("MagicNumber")
class App : JFrame("Ktvn Debugger"), Logging {
    init {
        // set up main frame
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setSize(1024, 768)

        val textArea = JTextArea()
        val background = JLabel().also {
            it.background = Color.BLACK
        }

        val imageAndStorySplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, background, textArea).also {
            it.resizeWeight = 0.5
        }

        val eventTerminalJTextArea = EventTerminalJTextArea().also {
            it.preferredSize = Dimension(0, 80)
            it.minimumSize = it.preferredSize
        }

        val centralSplitPane = JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            imageAndStorySplitPane,
            eventTerminalJTextArea
        ).also {
            it.resizeWeight = 0.8
        }

        this.layout = BorderLayout()

        // add panels to this frame
        this.add(createMenu(), BorderLayout.NORTH)
        this.add(centralSplitPane, BorderLayout.CENTER)

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

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {
                App()
            }
        }
    }
}
