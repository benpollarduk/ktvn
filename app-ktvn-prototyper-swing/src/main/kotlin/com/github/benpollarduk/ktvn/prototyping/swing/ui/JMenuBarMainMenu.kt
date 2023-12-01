package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.examples.shuttleLaunch.ShuttleLaunch
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.TheFateOfMorgana
import com.github.benpollarduk.ktvn.io.discovery.CatalogEntry
import com.github.benpollarduk.ktvn.io.discovery.VisualNovelCatalogResolver
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.game.GameSaveSerializer
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.io.restore.RestorePointSerializer
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.VisualNovel
import com.github.benpollarduk.ktvn.prototyping.swing.DebugGameEngine
import com.github.benpollarduk.ktvn.prototyping.swing.Severity
import com.github.benpollarduk.ktvn.prototyping.swing.components.EventTerminal
import java.awt.FlowLayout
import java.awt.Point
import java.awt.Rectangle
import java.io.File
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFileChooser
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Provides a JMenuBar for the main menu.
 */
@Suppress("TooManyFunctions", "MagicNumber")
class JMenuBarMainMenu(
    private val app: com.github.benpollarduk.ktvn.prototyping.swing.App,
    private val eventTerminal: EventTerminal,
    private val engine: DebugGameEngine
) : JMenuBar() {
    init {
        add(createGameMenu())
        add(createDataMenu())
        add(createViewMenu())
    }

    private fun loadRestorePoint(file: File) {
        val loadResult = RestorePointSerializer.fromFile(file.absolutePath)
        when (loadResult.result) {
            true -> app.applyRestorePoint(loadResult.loadedObject)
            false -> {
                JOptionPane.showMessageDialog(
                    app,
                    loadResult.message,
                    "Load Restore Point",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun saveRestorePoint(restorePoint: RestorePoint, path: String) {
        val saveResult = RestorePointSerializer.toFile(restorePoint, path)
        when (saveResult.result) {
            true -> return
            false -> {
                JOptionPane.showMessageDialog(
                    parent,
                    saveResult.message,
                    "Save Restore Point",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun loadGameSaveFile(file: File) {
        val loadResult = GameSaveSerializer.fromFile(file.absolutePath)
        when (loadResult.result) {
            true -> app.applyGameSave(loadResult.loadedObject)
            false -> {
                JOptionPane.showMessageDialog(
                    parent,
                    loadResult.message,
                    "Load Game Save",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun saveGameSaveFile(gameSave: GameSave, path: String) {
        val saveResult = GameSaveSerializer.toFile(gameSave, path)
        when (saveResult.result) {
            true -> return
            false -> {
                JOptionPane.showMessageDialog(
                    parent,
                    saveResult.message,
                    "Save Game Save",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun loadStepDataFile(file: File) {
        app.applyStepData(file.absolutePath)
    }

    fun saveStepDataFile(stepTracker: StepTracker, path: String) {
        val saveResult = stepTracker.persist(path)
        when (saveResult.result) {
            true -> return
            false -> {
                JOptionPane.showMessageDialog(
                    parent,
                    saveResult.message,
                    "Save Step Data",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun getFileNameExtensionFilter(type: String, ext: String): FileNameExtensionFilter {
        return FileNameExtensionFilter("$type (*.$ext)", ext)
    }

    private fun createRestorePointMenu(): JMenu {
        val loadRestorePointMenuItem = JMenuItem(LOAD_TITLE).also {
            it.addActionListener {
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(
                    RESTORE_POINT_TITLE,
                    RESTORE_POINT_EXTENSION
                )
                val returnValue = fileChooser.showOpenDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = fileChooser.selectedFile
                    loadRestorePoint(file)
                }
            }
        }

        val saveRestorePointAsMenuItem = JMenuItem(SAVE_AS_TITLE).also {
            it.addActionListener {
                val restorePoint = app.game?.getRestorePoint("") ?: return@addActionListener
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(
                    RESTORE_POINT_TITLE,
                    RESTORE_POINT_EXTENSION
                )
                val returnValue = fileChooser.showSaveDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = if (!fileChooser.selectedFile.name.endsWith(".$RESTORE_POINT_EXTENSION")) {
                        File("${fileChooser.selectedFile}.$RESTORE_POINT_EXTENSION")
                    } else {
                        fileChooser.selectedFile
                    }
                    saveRestorePoint(restorePoint, file.absolutePath)
                }
            }
        }

        return JMenu("Restore point").also {
            it.add(loadRestorePointMenuItem)
            it.add(saveRestorePointAsMenuItem)
        }
    }

    private fun createGameSaveMenu(): JMenu {
        val loadGameSaveMenuItem = JMenuItem(LOAD_TITLE).also {
            it.addActionListener {
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(GAME_SAVE_TITLE, GAME_SAVE_EXTENSION)
                val returnValue = fileChooser.showOpenDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = fileChooser.selectedFile
                    loadGameSaveFile(file)
                }
            }
        }

        val saveGameSaveAsMenuItem = JMenuItem(SAVE_AS_TITLE).also {
            it.addActionListener {
                val gameSave = app.game?.getGameSave() ?: return@addActionListener
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(GAME_SAVE_TITLE, GAME_SAVE_EXTENSION)
                val returnValue = fileChooser.showSaveDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = if (!fileChooser.selectedFile.name.endsWith(".$GAME_SAVE_EXTENSION")) {
                        File("${fileChooser.selectedFile}.$GAME_SAVE_EXTENSION")
                    } else {
                        fileChooser.selectedFile
                    }
                    saveGameSaveFile(gameSave, file.absolutePath)
                }
            }
        }

        return JMenu("Game Save").also {
            it.add(loadGameSaveMenuItem)
            it.add(saveGameSaveAsMenuItem)
        }
    }

    private fun createStepDataMenu(): JMenu {
        val loadStepDataMenuItem = JMenuItem(LOAD_TITLE).also {
            it.addActionListener {
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(STEP_DATA_TITLE, STEP_DATA_EXTENSION)
                val returnValue = fileChooser.showOpenDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = fileChooser.selectedFile
                    loadStepDataFile(file)
                }
            }
        }

        val saveStepDataAsMenuItem = JMenuItem(SAVE_AS_TITLE).also {
            it.addActionListener {
                val tracker = app.visualNovel?.configuration?.stepTracker ?: return@addActionListener
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(STEP_DATA_TITLE, STEP_DATA_EXTENSION)
                val returnValue = fileChooser.showSaveDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = if (!fileChooser.selectedFile.name.endsWith(".$STEP_DATA_EXTENSION")) {
                        File("${fileChooser.selectedFile}.$STEP_DATA_EXTENSION")
                    } else {
                        fileChooser.selectedFile
                    }
                    saveStepDataFile(tracker, file.absolutePath)
                }
            }
        }

        return JMenu("Step Data").also {
            it.add(loadStepDataMenuItem)
            it.add(saveStepDataAsMenuItem)
        }
    }

    private fun getCentralisedLocation(width: Int, height: Int): Point {
        // calculate the position to center the log window on the main window
        val mainWindow = SwingUtilities.getWindowAncestor(this)
        val mainWindowBounds = mainWindow?.bounds ?: Rectangle(0, 0, 800, 600)
        val x = (mainWindowBounds.width - width) / 2 + mainWindowBounds.x
        val y = (mainWindowBounds.height - height) / 2 + mainWindowBounds.y
        return Point(x, y)
    }

    private fun createViewMenu(): JMenu {
        val viewLogMenuItem = JMenuItem("Log...").also {
            it.addActionListener {
                val logFrame = JFrameLogViewer(engine.log)
                logFrame.location = getCentralisedLocation(logFrame.width, logFrame.height)
                logFrame.isVisible = true
            }
        }

        val viewStructureMenuItem = JMenuItem("Structure...").also {
            it.addActionListener {
                val structureFrame = JFrameVisualNovelStructureViewer(engine.getVisualNovelStructure())
                structureFrame.location = getCentralisedLocation(structureFrame.width, structureFrame.height)
                structureFrame.isVisible = true
            }
        }

        return JMenu("View").also {
            it.add(viewLogMenuItem)
            it.add(viewStructureMenuItem)
        }
    }

    private fun createDataMenu(): JMenu {
        return JMenu("Data").also {
            it.add(createRestorePointMenu())
            it.add(createGameSaveMenu())
            it.add(createStepDataMenu())
        }
    }

    private fun createGameMenu(): JMenu {
        val theFateOfMorganaDemoMenuItem = JMenuItem("The Fate Of Morgana").apply {
            addActionListener {
                app.beginGame(TheFateOfMorgana(), app.stepDataPath, app.gameSave, RestorePoint.EMPTY)
            }
        }

        val shuttleLaunchDemoMenuItem = JMenuItem("Shuttle;Launch").apply {
            addActionListener {
                app.beginGame(ShuttleLaunch(), app.stepDataPath, app.gameSave, RestorePoint.EMPTY)
            }
        }

        val examplesMenuItem = JMenu("Examples").apply {
            add(theFateOfMorganaDemoMenuItem)
            add(shuttleLaunchDemoMenuItem)
        }

        val importJarMenuItem = JMenuItem("Load VisualNovel from .jar...").also {
            it.addActionListener {
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = getFileNameExtensionFilter(
                    VISUAL_NOVEL_TITLE,
                    VISUAL_NOVEL_EXTENSION
                )
                val returnValue = fileChooser.showOpenDialog(app)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val file = fileChooser.selectedFile
                    loadStoryTemplateFromFile(file)
                }
            }
        }

        val unloadMenuItem = JMenuItem("Unload").also {
            it.addActionListener {
                app.unloadGame()
            }
        }

        return JMenu("Game").also {
            it.add(examplesMenuItem)
            it.add(importJarMenuItem)
            it.add(unloadMenuItem)
        }
    }

    private fun loadStoryTemplateFromFile(file: File) {
        val catalogEntries = VisualNovelCatalogResolver.resolveCatalogFromJar(file)
        val storyTemplates = catalogEntries.get()
        if (storyTemplates.size == 1) {
            app.beginGame(storyTemplates.first().entry, app.stepDataPath, app.gameSave, RestorePoint.EMPTY)
        } else if (storyTemplates.size > 1) {
            handleMultipleStoryTemplatesFoundInJar(storyTemplates)
        } else {
            eventTerminal.println(Severity.ERROR, "No visual novels found in ${file.name}.")
            JOptionPane.showMessageDialog(
                this,
                "No stories found in ${file.name}",
                "No stories were found.",
                JOptionPane.INFORMATION_MESSAGE
            )
        }
    }

    private fun handleMultipleStoryTemplatesFoundInJar(visualNovels: List<CatalogEntry<VisualNovel>>) {
        val dialog = JDialog(app, "Select Visual Novel", true)
        dialog.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        dialog.layout = FlowLayout()

        visualNovels.forEach { vn ->
            val button = JButton(vn.name)
            button.addActionListener {
                app.beginGame(vn.entry, app.stepDataPath, app.gameSave, RestorePoint.EMPTY)
                dialog.dispose()
            }
            dialog.add(button)
        }

        dialog.pack()
        dialog.setLocationRelativeTo(this)
        dialog.isVisible = true
    }

    private companion object {
        /**
         * Extension for [GameSave].
         */
        private const val GAME_SAVE_EXTENSION: String = "sav"

        /**
         * Extension for [StepTracker] data.
         */
        private const val STEP_DATA_EXTENSION: String = "stp"

        /**
         * Extension for [RestorePoint].
         */
        private const val RESTORE_POINT_EXTENSION: String = "json"

        /**
         * Extension for [VisualNovel].
         */
        private const val VISUAL_NOVEL_EXTENSION: String = "jar"

        /**
         * Title for [RestorePoint].
         */
        private const val RESTORE_POINT_TITLE: String = "Restore point"

        /**
         * Title for [GameSave].
         */
        private const val GAME_SAVE_TITLE: String = "Game save"

        /**
         * Title for [StepTracker] data.
         */
        private const val STEP_DATA_TITLE: String = "Step data"

        /**
         * Title for [VisualNovel].
         */
        private const val VISUAL_NOVEL_TITLE: String = "Visual novel"

        /**
         * Title for load menus.
         */
        private const val LOAD_TITLE: String = "Load..."

        /**
         * Title for save as menus.
         */
        private const val SAVE_AS_TITLE: String = "Save as..."
    }
}
