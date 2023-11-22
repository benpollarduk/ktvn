package com.github.benpollarduk.ktvn.swing

import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities
import org.apache.logging.log4j.kotlin.Logging

class App : JFrame("Ktvn Debugger"), Logging {
    init {
        // set up main frame
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setSize(1024, 768)
        this.layout = BorderLayout()

        this.isVisible = true
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
