package com.example.memorymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memorymaster.Adapters.EasyLevelAdapter
import com.example.memorymaster.Classes.BoardSize
import com.example.memorymaster.Classes.MemoryGame
import kotlinx.android.synthetic.main.activity_hard.*

class HardActivity : AppCompatActivity() {
    private lateinit var memoryGame: MemoryGame
    private lateinit var Adapter: EasyLevelAdapter
    private var boardSize: BoardSize = BoardSize.HARD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard)
        //Go Back on pressing back button
        hardArrowBack.setOnClickListener {
            finish()
        }

        memoryGame = MemoryGame(boardSize)


        //Setting Up the recycler view
        Adapter = EasyLevelAdapter(this , boardSize, memoryGame.cards, object: EasyLevelAdapter.cardClickListner{
            override fun onCardClicked(position: Int) {
                updateCardOnClick(position)
            }
        })
        rvHard.layoutManager = GridLayoutManager(this, boardSize.boardWidth())
        rvHard.adapter = Adapter
        rvHard.setHasFixedSize(true)
    }

    private fun updateCardOnClick(position: Int) {

        if (memoryGame.haveWon()){
            Toast.makeText(this, "You already Won the Game", Toast.LENGTH_SHORT).show()
            return
        }

        if (memoryGame.isCardFaceUp(position) == true){
            Toast.makeText(this, "Invalid Move", Toast.LENGTH_SHORT).show()
            return
        }

        if (memoryGame.flipCards(position)){
            tvPairsHard.text = "Pairs : ${memoryGame.foundPair}/${boardSize.cardPairsFormed()}"
            if (memoryGame.haveWon()){
                gameWonDialogueBox()
                return
            }
        }
        tvMovesHard.text = "Moves : ${memoryGame.getNumMoves()}"

        //memoryGame.flipCards(position)
        Adapter.notifyDataSetChanged()
    }


    private fun gameWonDialogueBox(){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Level Completed !!")
        alertDialog.setMessage("You completed the level in ${memoryGame.getNumMoves()} moves")
        alertDialog.setPositiveButton(
            "OK"
        ) { _, _ ->
            finish()
        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}


