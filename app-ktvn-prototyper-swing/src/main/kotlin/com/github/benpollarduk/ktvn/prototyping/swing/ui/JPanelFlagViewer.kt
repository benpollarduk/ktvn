package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.prototyping.swing.components.FlagViewer
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.table.DefaultTableModel

/**
 * Provides a JPanel for viewing flags.
 */
class JPanelFlagViewer : JPanel(), FlagViewer {
    private val flagsTable: JTable
    private val flagsData: MutableList<Array<String>> = mutableListOf()
    private val flagsTableModel = DefaultTableModel(
        flagsData.toTypedArray(),
        arrayOf("Flag", "State")
    )

    init {
        layout = BorderLayout()

        // create table
        flagsTable = JTable(flagsTableModel)

        // set up scroll panes
        val flagsScrollPane = JScrollPane(flagsTable)

        // set up heading
        val flagsLabel = JLabel("Flags")

        // add components
        add(flagsLabel, BorderLayout.NORTH)
        add(flagsScrollPane, BorderLayout.CENTER)
    }

    override fun updateFlags(flags: Flags) {
        reset()
        for (kv in flags.toMap()) {
            flagsTableModel.addRow(arrayOf(kv.key, kv.value))
        }
    }

    override fun reset() {
        flagsTableModel.rowCount = 0
    }
}
