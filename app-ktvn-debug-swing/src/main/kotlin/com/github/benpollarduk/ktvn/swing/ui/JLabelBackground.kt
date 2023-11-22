package com.github.benpollarduk.ktvn.swing.ui

import com.github.benpollarduk.ktvn.swing.components.Background
import java.awt.Dimension
import java.awt.Image
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JLabel

/**
 * Provides a UI control for displaying a background image based on a JLabel.
 */
public class JLabelBackground(
    override val resolutionWidth: Int,
    override val resolutionHeight: Int
) : JLabel(), Background {
    private var image: BufferedImage? = null

    init {
        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                val i = image ?: return
                setImage(i)
            }
        })
    }

    private fun scaleAndSetImage(image: BufferedImage) {
        // calculate the scaling factors and choose the minimum scaling factor to fit the image within the resolution
        val scaleWidth = resolutionWidth.toDouble() / image.width.toDouble()
        val scaleHeight = resolutionHeight.toDouble() / image.height.toDouble()
        val scaleFactor = minOf(scaleWidth, scaleHeight, 1.0)

        // resize the image
        val scaledImage = image.getScaledInstance(
            (image.width * scaleFactor).toInt(),
            (image.height * scaleFactor).toInt(),
            Image.SCALE_SMOOTH
        )

        // set the size of the JLabel to the scaled image size
        preferredSize = Dimension(scaledImage.getWidth(null), scaledImage.getHeight(null))

        icon = ImageIcon(scaledImage)
        revalidate()
        repaint()
    }

    override fun setImage(image: BufferedImage) {
        this.image = image
        scaleAndSetImage(image)
    }
}
