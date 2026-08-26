package com.leemra.battleship.ktor

import com.leemra.battleship.core.BattleShipCore
import com.leemra.battleship.core.Computer
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.concurrent.thread

class Server(host: String, port: Int) {
    var shipsPlaced = 0
    val server: EmbeddedServer<*, *> = embeddedServer(factory = Netty, port = port, host = host) {
        install(ContentNegotiation) {
            json(Json {
                serializersModule = SerializersModule {
                    contextual(FiredDataSerializer())
                }
            })
        }
        routing {
            post("/ship") {
                try {
                    val coords = call.receive<Coordinates>()
                    val shipList = placeShip(coords)
                    call.respond(
                        HttpStatusCode.OK, shipList
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            post("/fire") {
                try {
                    val coords = call.receive<Coordinates>()
                    val fired = fire(coords)
                    call.respond(
                        HttpStatusCode.OK, fired
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            post("/quit") {
                try {
                    call.respond(HttpStatusCode.OK)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                stop()
            }
        }
    }
    var core = BattleShipCore()
    var computer = Computer(core)

    init {
        thread(true, isDaemon = true) {
            server.start(wait = false)
        }
    }

    fun placeShip(coords: Coordinates): PlaceShip {
        when (shipsPlaced) {
            0 -> {
                if (core.placeCruiser(coords.x, coords.y, coords.orientation, true)) {
                    computer.setCruiser()
                    shipsPlaced++
                }
                return PlaceShip(core.list.toMutableList() as ArrayList<IntArray>)
            }

            1 -> {
                if (core.placeSub(coords.x, coords.y, coords.orientation, true)) {
                    computer.setSub()
                    shipsPlaced++
                }
                return PlaceShip(core.list.toMutableList() as ArrayList<IntArray>)
            }

            2 -> {
                if (core.placeDestroyer(coords.x, coords.y, true)) {
                    computer.setDestroyer()
                    shipsPlaced++
                }
                return PlaceShip(core.list.toMutableList() as ArrayList<IntArray>)
            }
        }
        // This code will never be reached, but the compiler insists on it.
        return PlaceShip(ArrayList())
    }

    fun fire(coords: Coordinates): FiredList {
        if (core.fire(coords.x, coords.y, true)) {
            return FiredList(ArrayList(), core.isGameOver, core.isWinner(true))
        } else {
            computer.fire()
            val winner = if (core.isWinner(true)) true else if (core.isWinner(false)) false else false
            return FiredList(computer.fired.toMutableList() as ArrayList<IntArray>, core.isGameOver, winner)
        }
    }

    fun stop() {
        server.stop(0, 0)
    }
}