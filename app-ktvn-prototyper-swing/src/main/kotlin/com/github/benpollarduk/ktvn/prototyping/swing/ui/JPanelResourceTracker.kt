package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.prototyping.swing.ResourceType
import com.github.benpollarduk.ktvn.prototyping.swing.components.ResourceTracker
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.table.DefaultTableModel

/**
 * Provides a JPanel that contains controls to track resources.
 */
class JPanelResourceTracker : JPanel(), ResourceTracker {
    private val resourcesTable: JTable
    private val resourcesData: MutableList<Array<String>> = mutableListOf()
    private val resourcesTableModel = DefaultTableModel(
        resourcesData.toTypedArray(),
        arrayOf("Key", "Required By", "Type", "Status")
    )

    init {
        layout = BorderLayout()

        // create table
        resourcesTable = JTable(resourcesTableModel)

        // set up scroll pane
        val resourcesScrollPane = JScrollPane(resourcesTable)

        // set up heading
        val resourcesLabel = JLabel("Resources")

        // add components
        add(resourcesLabel, BorderLayout.NORTH)
        add(resourcesScrollPane, BorderLayout.CENTER)
    }

    private fun convertResourceTypeToString(type: ResourceType): String {
        return when (type) {
            ResourceType.CHARACTER -> "character"
            ResourceType.BACKGROUND -> "background"
            ResourceType.MUSIC -> "music"
            ResourceType.SOUND_EFFECT -> "sfx"
        }
    }

    override fun registerResourceLocated(key: String, requiredBy: String, type: ResourceType) {
        resourcesTableModel.addRow(arrayOf(key, requiredBy, convertResourceTypeToString(type), "found"))
    }

    override fun registerResourceMissing(key: String, requiredBy: String, type: ResourceType) {
        resourcesTableModel.addRow(arrayOf(key, requiredBy, convertResourceTypeToString(type), "missing"))
    }

    override fun reset() {
        resourcesTableModel.rowCount = 0
    }
}
