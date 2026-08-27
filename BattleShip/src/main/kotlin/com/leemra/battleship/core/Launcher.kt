package com.leemra.battleship.core

import com.leemra.battleship.IController
import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class Launcher : Application() {
    lateinit var controller: IController
    override fun start(stage: Stage) {
        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("launcher.fxml"))
            val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
            controller = fxmlLoader.getController<IController>()
            controller.app = this
            stage.minWidth = Screen.getPrimary().visualBounds.width * .66
            stage.minHeight = Screen.getPrimary().visualBounds.height * .66
            stage.title = "BattleShip"
            scene.stylesheets.add("atlantafx/cupertino-dark.css")
            stage.scene = scene
            stage.show()
        } catch (_: Exception) {
        }
    }

    /** The native system shutdown methods are not functional at this point.<br>
     * Both exitProcess(0) and System.exit(0) hang indefinitely.<br>
     * I have isolated the root cause to the server, but there is nothing I can do to make the server stop (to my knowledge).<br>
     * Except, possibly, for visiting "https://localhost:8080/quit", but I'm not even sure that works.<br>
     * So I made this. It should do the same thing as System.exit(0).<br>
     * I left out log files, under the YAGNI (You Aren't Gonna Need It) principle.<br>
     */
    override fun stop() {
        controller.onStop()
        try {
            Platform.exit()
        } catch (_: Exception) {
        }
        try {
            System.out.flush()
            System.err.flush()
        } catch (_: Exception) {
        }
        System.gc()
        Runtime.getRuntime().halt(0)

    }
}