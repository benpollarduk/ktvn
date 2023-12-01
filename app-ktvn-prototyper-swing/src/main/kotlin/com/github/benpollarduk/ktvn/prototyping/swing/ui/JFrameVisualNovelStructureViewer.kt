package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.logic.structure.StepIdentifier
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.ScrollPaneConstants
import javax.swing.border.EmptyBorder
import javax.swing.tree.DefaultMutableTreeNode

/**
 * Provides a viewer for a visual novel structure.
 */
@Suppress("MagicNumber")
class JFrameVisualNovelStructureViewer(structure: Array<StepIdentifier>) : JFrame("Structure Viewer") {
    init {
        if (structure.any()) {
            val root = DefaultMutableTreeNode("Story")
            var currentChapter = 1
            var currentScene = 1
            var currentChapterNode = DefaultMutableTreeNode("Chapter 1")
            var currentSceneNode = DefaultMutableTreeNode("Scene 1")

            for (id in structure) {
                if (id.chapter > currentChapter) {
                    currentChapterNode.add(currentSceneNode)
                    currentSceneNode = DefaultMutableTreeNode("Scene $currentScene")
                    root.add(currentChapterNode)
                    currentChapter = id.chapter
                    currentScene = id.scene
                    currentChapterNode = DefaultMutableTreeNode("Chapter $currentChapter")
                }

                if (id.scene > currentScene) {
                    currentChapterNode.add(currentSceneNode)
                    currentScene = id.scene
                    currentSceneNode = DefaultMutableTreeNode("Scene $currentScene")
                }

                currentSceneNode.add(DefaultMutableTreeNode("Step ${id.step}"))
            }

            currentChapterNode.add(currentSceneNode)
            root.add(currentChapterNode)

            val tree = JTree(root).also {
                it.border = EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN)
            }

            val scrollPane = JScrollPane(tree)
            scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
            scrollPane.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER

            contentPane.add(scrollPane, BorderLayout.CENTER)
        }

        defaultCloseOperation = DISPOSE_ON_CLOSE
        size = Dimension(400, 600)
    }

    private companion object {
        /**
         * Get the margin.
         */
        private const val MARGIN: Int = 10
    }
}
