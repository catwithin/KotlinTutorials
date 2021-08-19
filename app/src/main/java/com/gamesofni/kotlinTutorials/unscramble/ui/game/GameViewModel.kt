package com.gamesofni.kotlinTutorials.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
    private val _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()


    val score: LiveData<Int>
        get() = _score
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount
    // notice LiveData type - immutable
    val currentScrambledWord: LiveData<String>
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

        _currentScrambledWord.value = String(tempWord)
        _currentWordCount.value = (_currentWordCount.value)?.inc()
        _usedWordsList.add(_currentWord)
    }

    /*
    * Whether should keep playing the game
    * Updates the next word if so
    */
    fun stillPlaying(): Boolean {
        return if (currentWordCount.value!! < MAX_NO_OF_WORDS) {
            setupNextWord()
            true
        } else false
    }


    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(_currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        _usedWordsList.clear()
        setupNextWord()
    }

}