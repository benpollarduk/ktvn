package com.github.benpollarduk.ktvn.swing.ui

import com.github.benpollarduk.ktvn.swing.components.Background
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JLabel

/**
 * Provides a UI control for displaying a background image based on a JLabel.
 */
public class JLabelBackground : JLabel(), Background {
    override fun set(image: BufferedImage) {
        icon = ImageIcon(image)
    }
}
