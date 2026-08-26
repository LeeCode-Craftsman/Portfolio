package com.leemra.battleship.ktor

import com.leemra.battleship.IController
import com.leemra.battleship.core.ClassicBattleShip
import com.leemra.battleship.core.Launcher
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

class KtorGameController() : IController {
    override var app: Application? = null
        set(value) {
            field = value
            try {
                (value as Launcher).controller = this
            } catch (_: Exception) {
            }
        }
    var oldController: IController? = null
    lateinit var launcher: Scene
    lateinit var stage: Stage
    val playerBoard = Array(10) { arrayOfNulls<Button>(10) }
    val cpBoard = Array(10) { arrayOfNulls<Button>(10) }
    val location = "http://localhost:8080"
    var playerClicks = 0
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                serializersModule = SerializersModule {
                    contextual(FiredDataSerializer())
                }
            })
        }
    }

    @FXML
    lateinit var cpuPanel: GridPane

    @FXML
    lateinit var playerPanel: GridPane

    @FXML
    lateinit var color: ToggleGroup

    @FXML
    lateinit var theme: ToggleGroup

    @FXML
    lateinit var status: Label

    @FXML
    lateinit var direction: ToggleGroup

    @FXML
    lateinit var horizontal: ToggleButton

    @FXML
    lateinit var vertical: ToggleButton


    fun initialize() {
        for (i in 0..<playerBoard.size) {
            for (j in 0..<playerBoard[i].size) {
                playerBoard[i][j] = Button()
                playerBoard[i][j]!!.prefWidth = 40.0
                playerBoard[i][j]!!.prefHeight = 40.0
                playerBoard[i][j]!!.onAction = EventHandler { event -> playerBoardClick(event, i, j) }
                playerPanel.add(playerBoard[i][j], i, j)
            }
        }
        for (i in 0..<cpBoard.size) {
            for (j in 0..<cpBoard[i].size) {
                cpBoard[i][j] = Button()
                cpBoard[i][j]!!.prefWidth = 40.0
                cpBoard[i][j]!!.prefHeight = 40.0
                cpBoard[i][j]!!.isDisable = true
                cpBoard[i][j]!!.onAction = EventHandler { event -> cpBoardClick(event, i, j) }
                cpuPanel.add(cpBoard[i][j], i, j)
            }
        }
    }

    fun playerBoardClick(event: ActionEvent, x: Int, y: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val ships = placeShip(x, y)
            if (ships.isEmpty()) playerClicks--
            javafx.application.Platform.runLater {
                for (ship in ships) {
                    playerBoard[ship[0]][ship[1]]?.text = "~"
                }
            }

            playerClicks++
            javafx.application.Platform.runLater {
                when (playerClicks) {
                    1 -> {
                        status.text = "Place a Submarine"
                        horizontal.text = "\\"
                        vertical.text = "/"
                    }

                    2 -> {
                        status.text = "Place a Destroyer"
                        horizontal.isVisible = false
                        vertical.isVisible = false
                    }

                    3 -> {
                        status.text = "Fire"
                        playerBoard.forEach { line ->
                            line.forEach { button ->
                                button?.isDisable = true
                            }
                        }
                        cpBoard.forEach { line ->
                            line.forEach { button ->
                                button?.isDisable = false
                            }
                        }
                    }
                }
            }
        }
    }

    fun cpBoardClick(event: ActionEvent, x: Int, y: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val shots = fire(x, y)
            javafx.application.Platform.runLater {
                if (shots.gameOver) {
                    cpBoard.forEach { line ->
                        line.forEach { button ->
                            button?.isDisable = true
                        }
                    }
                    status.text = "Game Over: " + (if (shots.playerWinner) "Player" else "Computer") + " Wins"
                }
                if (shots.list.isNotEmpty()) {
                    cpBoard[x][y]?.text = "*"
                    cpBoard[x][y]?.isDisable = true
                    for (shot in shots.list) {
                        if (playerBoard[shot[0]][shot[1]]?.text == "~") playerBoard[shot[0]][shot[1]]?.text = "X"
                        else playerBoard[shot[0]][shot[1]]?.text = "*"
                    }
                } else {
                    cpBoard[x][y]?.text = "X"
                    cpBoard[x][y]?.isDisable = true
                }
            }
        }
    }

    suspend fun placeShip(x: Int, y: Int): ArrayList<IntArray> {
        val message = Coordinates(x, y, if (horizontal.isSelected) 0 else 1)
        val response = client.post("$location/ship") {
            contentType(ContentType.Application.Json)
            setBody(message)
        }
        val ships = response.body<PlaceShip>()
        return ships.list
    }

    suspend fun fire(x: Int, y: Int): FiredList {
        val message = Coordinates(x, y, 0)
        val response = client.post("$location/fire") {
            contentType(ContentType.Application.Json)
            setBody(message)
        }
        return response.body<FiredList>()
    }

    suspend fun quit() {
        client.post("$location/quit")
    }

    fun onQuitClick(event: ActionEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                quit()
                client.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val css = stage.scene.stylesheets
        stage.scene = launcher
        oldController?.app = app
        stage.scene.stylesheets.clear()
        stage.scene.stylesheets.setAll(css)
        stage.show()
    }

    fun onThemeChange(event: ActionEvent) {
        val name = (theme.selectedToggle as RadioMenuItem).text.lowercase()
        val type = (color.selectedToggle as RadioMenuItem).text.lowercase()
        val current = (event.source as RadioMenuItem).parentPopup.ownerNode.scene
        current.stylesheets.clear()
        if (name != "dracula") current.stylesheets.add("/atlantafx/$name-$type.css")
        else current.stylesheets.add("/atlantafx/dracula.css")
    }

    override fun onStop() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                quit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        client.close()
        javafx.application.Platform.exit()
    }
}

class LauncherController() : IController {
    override var app: Application? = null
        set(value) {
            field = value
            try {
                (value as Launcher).controller = this
            } catch (_: Exception) {
            }
        }

    @FXML
    lateinit var color: ToggleGroup

    @FXML
    lateinit var theme: ToggleGroup

    fun onThemeChange(event: ActionEvent) {
        val name = (theme.selectedToggle as RadioMenuItem).text.lowercase()
        val type = (color.selectedToggle as RadioMenuItem).text.lowercase()
        val current = (event.source as RadioMenuItem).parentPopup.ownerNode.scene
        current.stylesheets.clear()
        if (name != "dracula") current.stylesheets.add("/atlantafx/$name-$type.css")
        else current.stylesheets.add("/atlantafx/dracula.css")
    }

    fun onClassicClick(event: ActionEvent) {
        val classicBattleShip = ClassicBattleShip(app)
        classicBattleShip.run((event.source as Node).scene.window as Stage)
    }

    fun onNewClick(event: ActionEvent) {
        val newBattleShip = NewBattleShip(app, this)
        newBattleShip.run((event.source as Node).scene.window as Stage)
    }

    override fun onStop() {
        javafx.application.Platform.exit()
    }
}