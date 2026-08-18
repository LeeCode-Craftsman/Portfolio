// Auto-generated class
package com.leemra.meterReader


import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.image.Image
import java.awt.Taskbar
import java.awt.Toolkit
import java.awt.Taskbar.Feature

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("meter-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.minHeight = 500.0
        stage.minWidth = 900.0
        stage.title = "Meter Training"
        stage.icons.add(Image("logo.png"))
        if (Taskbar.isTaskbarSupported()) {
            val taskbar = Taskbar.getTaskbar()
            if (taskbar.isSupported(Feature.ICON_IMAGE)) {
                val toolkit = Toolkit.getDefaultToolkit()
                val dockIcon = toolkit.getImage("icon.png")
                taskbar.setIconImage(dockIcon)
            }

        }
        scene.stylesheets.add("Styles.css")
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}