package com.example.tictactoe

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_player_names.*
import android.util.Pair as UtilPair


class addPlayerNamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player_names)

        edittxt1.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        edittxt2.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })
    }


    fun startGame(view: View) {
        val player1 = edittxt1.text.toString().trim().lowercase()
        val player2 = edittxt2.text.toString().trim().lowercase()
        val intent = Intent(this, MainActivity::class.java)

        val optionsX = UtilPair.create<View?, String?>(player_x, "transition_x")
        val optionsO = UtilPair.create<View, String>(player_o, "transition_o")
        val options1 = UtilPair.create<View?, String?>(edittxt1, "player1name")
        val options2 = UtilPair.create<View, String>(edittxt2, "player2name")
        val options = ActivityOptions.makeSceneTransitionAnimation(this, optionsX, optionsO, options1, options2)
        if (player1.isNotEmpty() && player2.isNotEmpty()){
            intent.putExtra("player1",player1)
            intent.putExtra("player2",player2)
        }
        else if (player1.isNotEmpty()){
            intent.putExtra("player1",player1)
            intent.putExtra("player2","player 2")
        }
        else if (player2.isNotEmpty()){
            intent.putExtra("player1","player 1")
            intent.putExtra("player2",player2)
        }
        else {
            intent.putExtra("player1","player 1")
            intent.putExtra("player2","player 2")
        }


        startActivity(intent,options.toBundle())

        overridePendingTransition(0, 0)
    }
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}