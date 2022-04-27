package com.example.tictactoe

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.exit_dialog.*
import kotlinx.android.synthetic.main.win_dialog.*
import kotlinx.android.synthetic.main.win_dialog.playAgainbtn
import kotlinx.android.synthetic.main.win_dialog.winnerText1
import kotlinx.android.synthetic.main.win_dialog.winnerText2


class MainActivity : AppCompatActivity() {
    private var player1 = arrayListOf<Int>()
    private var player2 = arrayListOf<Int>()

    lateinit var name1:String
    lateinit var name2:String

    var currentPlayer = 1
    var attemptsCount = 9
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_main)

         name1 = intent.getStringExtra("player1").toString()
         name2 = intent.getStringExtra("player2").toString()

        player1Name.setText(name1)
        player2Name.setText(name2)


    }
    override fun onBackPressed() {
        if (player1.isNotEmpty() || player2.isNotEmpty()){
            val  mDialogView = LayoutInflater.from(this).inflate(R.layout.exit_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
            mBuilder.setView(mDialogView)
            val mDialog = mBuilder.create()
            mDialog.show()
            mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            mDialog.exitbtn.setOnClickListener {
                mDialog.dismiss()
                super.onBackPressed()
                Toast.makeText(this, "all progress is lost.", Toast.LENGTH_SHORT).show()
            }
            mDialog.dismissbtn.setOnClickListener { mDialog.dismiss() }
        }
        else{super.onBackPressed() }

    }
    fun ticButton(view: View) {
        val buttonSelected = view as Button
        var cellId = 0
        when(buttonSelected.id){
            R.id.button1 -> cellId = 1
            R.id.button2 -> cellId = 2
            R.id.button3 -> cellId = 3
            R.id.button4 -> cellId = 4
            R.id.button5 -> cellId = 5
            R.id.button6 -> cellId = 6
            R.id.button7 -> cellId = 7
            R.id.button8 -> cellId = 8
            R.id.button9 -> cellId = 9
        }
        playGame(cellId,buttonSelected)

    }

    private fun playGame(cellId : Int,buttonSelected : Button) {
        attemptsCount -= 1
        if (currentPlayer== 1){
            player1namebox.setBackgroundResource(R.drawable.shape_clicked)
            player2namebox.setBackgroundResource(R.drawable.stroke_box_bg)
            buttonSelected.background =ContextCompat.getDrawable(this,R.drawable.tic_x)
            player1.add(cellId)
            currentPlayer = 2
        }
        else {
            player1namebox.setBackgroundResource(R.drawable.stroke_box_bg)
            player2namebox.setBackgroundResource(R.drawable.shape_clicked)

            buttonSelected.background =ContextCompat.getDrawable(this,R.drawable.tic_o)
            player2.add(cellId)
            currentPlayer = 1
        }
        buttonSelected.isEnabled = false

        checkWinner()
    }

    private fun checkWinner() {
        var winner = 0

        //Rows
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) winner = 1
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) winner = 2

        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) winner = 1
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)) winner = 2

        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) winner = 1
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)) winner = 2

        //columns

        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) winner = 1
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)) winner = 2

        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) winner = 1
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)) winner = 2

        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) winner = 1
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)) winner = 2

        // X shape

        if (player1.contains(1) && player1.contains(5) && player1.contains(9)) winner = 1
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)) winner = 2

        if (player1.contains(3) && player1.contains(5) && player1.contains(7)) winner = 1
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)) winner = 2


        if (winner == 1 || winner==2){
            showdialog(winner)
        }
        else if (attemptsCount== 0)
            showdialog(winner)
    }

    private fun showdialog(winner: Int) {

        val  mDialogView = LayoutInflater.from(this).inflate(R.layout.win_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setView(mDialogView)

        val mDialog = mBuilder.create()
        mDialog.show()
        mDialog.setCanceledOnTouchOutside(false)

        mDialog.setOnCancelListener {
            finish()
            overridePendingTransition(0, 0)
            startActivity(getIntent())
            overridePendingTransition(0, 0)
        }
        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val winText1 = mDialog.winnerText1
        val winText2 = mDialog.winnerText2

        if (winner == 1){
            winText1.setText("$name1 won this game !")
            winText2.setText("Hard luck $name2")

        }


        if (winner == 2){
            winText1.setText("$name2 won this game !")
            winText2.setText("Hard luck $name1")

        }

        else if (winner == 0){
            winText1.setText("Well played both of you")
            winText2.setText("It's a tie, play again !")

        }


        mDialog.playAgainbtn.setOnClickListener {
            mDialog.dismiss()
            finish()
            overridePendingTransition(0, 0)
            startActivity(getIntent())
            overridePendingTransition(0, 0)


            super.onBackPressed()
        }

    }


}