package com.leemra.battleship

import javafx.application.Application

interface IController {
    var app: Application?
    fun onStop()
}