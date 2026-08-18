// Copyright (c), Liam Emra
package com.leemra.meterReader


import com.io7m.digal.core.DialValueConverterRealType
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import java.util.*
import kotlin.properties.Delegates


class MeterReaderController {
    @FXML
    private lateinit var menuBox: VBox
    @FXML
    private lateinit var borderPane: BorderPane
	@FXML
	private lateinit var scorePane: VBox
	@FXML
	private lateinit var optionsPanel: VBox
	@FXML
	private lateinit var startPanel: VBox
    @FXML
    private lateinit var levelGroup: ToggleGroup
	@FXML
	private lateinit var typeGroup: ToggleGroup
	@FXML
	private lateinit var infoLabel: Label
    @FXML
    private lateinit var dialBox: HBox
	@FXML
	private lateinit var box1: TextField
	@FXML
	private lateinit var box2: TextField
	@FXML
	private lateinit var box3: TextField
	@FXML
	private lateinit var box4: TextField
	@FXML
	private lateinit var box5: TextField
	@FXML
	private lateinit var scoreLabel: Label
	@FXML
	private lateinit var questionSpinner: Spinner<Int>

    private val dials = arrayOf(MeterDial(true), MeterDial(false), MeterDial(true), MeterDial(false), MeterDial(true))
	private var inBoxes = emptyArray<TextField>()

	private val boxes = arrayOf(VBox(Label("0"), dials[4], Label("5")),
		VBox(Label("0"), dials[3], Label("5")),
		VBox(Label("0"), dials[2], Label("5")),
		VBox(Label("0"), dials[1], Label("5")),
		VBox(Label("0"), dials[0], Label("5")))
	private val rand = Random()

	private var count by Delegates.observable(0) { _, _, _ ->
		updateInfoLabel()
	}
	private var MAX by Delegates.observable(50) { _, _, _ ->
		updateInfoLabel()
	}
	private var score = 0

	fun initialize() {
		initDials()
		initBoxes()
		dialBox.children.addAll(boxes)
		inBoxes = arrayOf(box1, box2, box3, box4, box5)
		questionSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 50)
		questionSpinner.editor.textProperty().addListener { _, _, new -> MAX = new.toInt() }
		infoLabel.text = "Read the Meter - 1 Box per Dial - ${MAX - count} Questions Remaining"
	}

    fun onStartButtonClick() {
		val selected : ToggleButton = levelGroup.selectedToggle as ToggleButton
		count = 0
		score = 0
		if (selected.text.equals("1")) startLevel(numbers = true, dialTicks = true)
		else if (selected.text.equals("2")) startLevel(numbers = false, dialTicks = true)
		else startLevel(numbers = false, dialTicks = false)
		showPane(menuBox, borderPane)
    }

	fun onOptionsButtonClick() {
		showPane(startPanel, optionsPanel)
	}

	fun onBackButtonClick() {
		showPane(optionsPanel, startPanel)
	}

	fun enterButtonClick() {
		try {
			val inputs = IntArray(5)
			for (i in 0 until 5) inputs[i] = dials[i].convertedValue().get().toInt()
			validate(inputs)
		} catch (_: Exception) {
			val dialog = Alert(Alert.AlertType.ERROR, "Please enter single digits in each input box.")
			dialog.showAndWait()
		}
		box5.requestFocus()
	}

	fun quitButtonClick() {
		showPane(borderPane, menuBox)
		for (box in inBoxes) {
			box.border = null
			box.text = ""
		}
	}

	fun returnButtonClick() {
		showPane(scorePane, menuBox)
	}

	private fun validate(inputs: IntArray) {
		val selected : ToggleButton = typeGroup.selectedToggle as ToggleButton
		if (selected.text.equals("Practice")) {
			if (inputs[0].toString() == box1.text && inputs[1].toString() == box2.text &&
				inputs[2].toString() == box3.text && inputs[3].toString() == box4.text &&
				inputs[4].toString() == box5.text){
				setDials()
				for (box in inBoxes) {
					box.text = ""
					box.border = null
				}
				count++
				if (count == MAX) showPane(borderPane, menuBox)
				return
			} else {
				recolor(inputs)
			}
			val dialog = Alert(Alert.AlertType.INFORMATION, "Incorrect values, please retry.")
			dialog.showAndWait()

		} else {
			setDials()
			count++
			if (inputs[0].toString() == inBoxes[0].text && inputs[1].toString() == inBoxes[1].text &&
				inputs[2].toString() == inBoxes[2].text && inputs[3].toString() == inBoxes[3].text &&
				inputs[4].toString() == inBoxes[4].text) score++
			box1.text = ""; box2.text = ""; box3.text = ""; box4.text = ""; box5.text = ""
			if (count == MAX) {
				setUpScore()
			}
		}
	}

	private fun recolor(inputs: IntArray) {
		val errorBorder =  Border(BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN))
		for (i in 0 until 5) {
			if (!inBoxes[i].text.equals(inputs[i].toString())) inBoxes[i].border = errorBorder
			else inBoxes[i].border = null
		}
	}


	private fun setUpScore() {
		showPane(borderPane, scorePane)
		scoreLabel.text = "Score: ${kotlin.math.round((score.toDouble()/MAX.toDouble()) * 1000.0)/10.0}%\r\n$score out of $MAX Correct"
	}

    private fun startLevel(numbers: Boolean, dialTicks: Boolean) {
		if (dialTicks) {
			for (dial in dials) dial.styleClass.remove("no-tick-color")
		} else {
			for (dial in dials) dial.styleClass.addFirst("no-tick-color")
		}
		for (box in boxes) {
			box.children[0].isVisible = numbers
			box.children[0].isManaged = numbers
			box.children[2].isVisible = numbers
			box.children[2].isManaged = numbers
		}
		setDials()
    }

	private fun showPane(current: Pane, new: Pane) {
		current.isVisible = false
		current.isManaged = false
		new.isVisible = true
		new.isManaged = true
	}

	private fun setDials() {
		val dial = arrayOf("", "", "", "", "")
		dial[0] = (rand.nextInt(0, 10)).toString()
		dial[1] = rand.nextInt(0, 10).toString()
		dial[2] = rand.nextInt(0, 10).toString()
		dial[3] = rand.nextInt(0, 10).toString()
		dial[4] = rand.nextInt(0, 10).toString()
		for (i in 0 until 5) {
			var number = ""
			for (j in i downTo 0) {
				number += dial[j]
				if (j == i) number += "."
				if (j == 0 && i == 0) number += "0"
			}
			dials[i].convertedValue = number.toDouble()
		}
	}

	private fun initBoxes() {
		for (box in boxes) box.alignment = Pos.CENTER
	}

	private fun initDials() {
		for (dial in dials) {
			dial.prefWidth = 100.0
			dial.prefHeight = 100.0
			dial.styleClass.add("dial")
			dial.maxHeight = dial.prefHeight
			dial.setTickCount(10)
			dial.isVisible = true
			dial.isDisable = true
			dial.setValueConverter(object : DialValueConverterRealType {
				override fun convertToDial(x: Double): Double {
					return x / 10
				}

				override fun convertFromDial(x: Double): Double {
					return x * 10
				}

				override fun convertedNext(x: Double): Double {
					return x + 0.5
				}

				override fun convertedPrevious(x: Double): Double {
					return x - 0.5
				}
			})
		}
	}

	private fun updateInfoLabel() {
		if (MAX - count > 1) infoLabel.text = "Read the Meter - 1 Box per Dial - ${MAX - count} Questions Remaining"
		else infoLabel.text = "Read the Meter - 1 Box per Dial - ${MAX - count} Question Remaining"
	}
}