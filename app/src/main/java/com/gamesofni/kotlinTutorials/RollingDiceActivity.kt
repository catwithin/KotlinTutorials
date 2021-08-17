package com.gamesofni.kotlinTutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class RollingDiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rolling_dice)
        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener {
            val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            toast.show()

//            val resultTextView: TextView = findViewById(R.id.rolledNumber)
//            resultTextView.text = "rolledNumber"

            rollDice()
        }
    }

    private fun rollDice() {
        val dice = Dice(6)
        val rollResult = dice.roll()
        val resultTextView: TextView = findViewById(R.id.rolledNumber)
        resultTextView.text = rollResult.toString()

        val diceImage: ImageView = findViewById(R.id.diceImg)
        val drawableResource = when (rollResult) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_6
        }

        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = rollResult.toString()


        val luckyNumber = 4


//        if (rollResult == luckyNumber) {
//            println("You win!")
//        } else if (rollResult == 1) {
//            println("So sorry! You rolled a 1. Try again!")
//        } else {
//            println("Apologies! You rolled a 6. Try again!")
//        }

        when (rollResult) {
            luckyNumber -> println("You won!")
            1 -> println("So sorry! You rolled a 1. Try again!")
            else -> println("Generic Fail")
        }
    }
}


class Dice(private val numSides: Int) { // private prop

    fun roll(): Int {
        return (1..numSides).random()
    }
}
