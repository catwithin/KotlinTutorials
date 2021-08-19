package com.gamesofni.kotlinTutorials.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String


    val score: Int
        get() = _score
    val currentWordCount : Int
        get() = _currentWordCount
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var _usedWordsList: MutableList<String> = mutableListOf()
    private lateinit var _currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        setupNextWord()
    }

    //  The ViewModel is destroyed when the associated fragment is detached, or when the activity is finished.
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    private fun setupNextWord() {
        _currentWord = allWordsList.random()

        if (_usedWordsList.contains(_currentWord)) {
            setupNextWord()
        }

        val tempWord = _currentWord.toCharArray()
//        TODO: there seems to be a bug in the tutorial (it compares not strings but object ids,
//         which are always not equal)
//        Log.d("GameFragment", "Initial: ${String(tempWord)}")
        while (String(tempWord).equals(_currentWord, false)) {
            tempWord.shuffle()
//            Log.d("GameFragment", "After shuffle: ${String(tempWord)}")
        }
//        Log.d("GameFragment", "Final: ${String(tempWord)}")

        _currentScrambledWord = String(tempWord)
        ++_currentWordCount
        _usedWordsList.add(_currentWord)
    }

    /*
    * Whether should keep playing the game
    * Updates the next word if so
    */
    fun stillPlaying(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            setupNextWord()
            true
        } else false
    }


    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(_currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        _usedWordsList.clear()
        setupNextWord()
    }

}