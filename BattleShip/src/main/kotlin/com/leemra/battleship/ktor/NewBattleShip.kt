package com.leemra.battleship.ktor

import com.leemra.battleship.IController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class NewBattleShip(val app: Application?, val oldController: IController) {
    fun run(stage: Stage) {
        val loader = FXMLLoader(NewBattleShip::class.java.getResource("battleship-new.fxml"))
        val css = stage.scene.stylesheets
        val old = stage.scene
        stage.scene = Scene(loader.load(), 900.0, 600.0)
        val controller = loader.getController<KtorGameController>()
        controller.launcher = old
        controller.app = this.app
        controller.oldController = oldController
        controller.stage = stage
        stage.scene.stylesheets.setAll(css)
        stage.show()
    }
}