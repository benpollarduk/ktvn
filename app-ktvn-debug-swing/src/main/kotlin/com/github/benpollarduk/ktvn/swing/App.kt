package com.github.benpollarduk.ktvn.swing

import com.github.benpollarduk.ktvn.swing.ui.EventTerminalJTextArea
import java.awt.BorderLayout
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter
import org.apache.logging.log4j.kotlin.Logging

class App : JFrame("Ktvn Debugger"), Logging {
    init {
        // set up main frame
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setSize(1024, 768)
        this.layout = BorderLayout()

        // add panels to this frame
        this.add(createMenu(), BorderLayout.NORTH)
        this.add(EventTerminalJTextArea(), BorderLayout.SOUTH)

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
