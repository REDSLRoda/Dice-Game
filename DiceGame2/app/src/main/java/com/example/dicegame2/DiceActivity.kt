package com.example.dicegame2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class DiceActivity : AppCompatActivity() {

    lateinit var diceText: TextView//the value of the dice adition
    lateinit var EnterNum: TextView//the textview for target value entered by user
    lateinit var HWin: TextView//number of human wins
    lateinit var CWin: TextView//number of computer wins

//random variables for dice throw
//first dice
    var rInt1 = Random().nextInt(6) + 1

    //second dice
    var rInt2 = Random().nextInt(6) + 1

    //third dice
    var rInt3 = Random().nextInt(6) + 1

    //fourth dice
    var rInt4 = Random().nextInt(6) + 1

    //fifth dice
    var rInt5 = Random().nextInt(6) + 1

    //computer dice roll //computer first dice
    var rInt6 = Random().nextInt(6) + 1

    //computer second dice
    var rInt7 = Random().nextInt(6) + 1

    //computer third dice
    var rInt8 = Random().nextInt(6) + 1

    //computer forth dice
    var rInt9 =Random().nextInt(6) + 1

    //computer fifth dice
    var rInt10 = Random().nextInt(6) + 1

    //random variables for  used for dice score wich shows the value of all five dices
    var randomInt1 = Random().nextInt(6) + 1
    //first dice
    var randomInt2 = Random().nextInt(6) + 1
    //second dice
    var randomInt3 = Random().nextInt(6) + 1
    //third dice
    var randomInt4 = Random().nextInt(6) + 1
    //fourth dice
    var randomInt5 = Random().nextInt(6) + 1
    //fifth
    //computer dice roll //computer first dice
    var randomInt6 = Random().nextInt(6) + 1
    //computer second dice
    var randomInt7 = Random().nextInt(6) + 1
    //computer third dice
    var randomInt8 = Random().nextInt(6) + 1
    //computer forth dice
    var randomInt9 = Random().nextInt(6) + 1
    //computer fifth dice
    var randomInt10 = Random().nextInt(6) + 1

    var ranPlay = intArrayOf(randomInt1,randomInt2,randomInt3,randomInt4,randomInt5)//array of random throws as intiger array
    var comPlay = intArrayOf(randomInt6,randomInt7,randomInt8,randomInt9,randomInt10)//array of random throws as intiger array for computer



    var player = 0 //the player
    var computer = 0 //the computer
    var playerscore = 0 //player's score
    var computerscore = 0 //computer's score
    lateinit var Numberinput: EditText //the target value entered by user
    var count = 0 //number of throws
    var press = 0 //number of score function called
    var Win = 0 //wins
    var playerWins=0 //number of player wins
    var computerWins=0 //computer wins
    var die = 0
    private lateinit var mediaPlayer: MediaPlayer//media player used for dice roll sound
    val choose = arrayListOf<Boolean>(false, false, false, false, false) //the array of dices chosen by player to keep


    lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("MissingInflatedId", "CutPasteId", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        sharedPreferences = getSharedPreferences("",Context.MODE_PRIVATE)
        playerWins = sharedPreferences.getInt("userWins",0)
//dice images used
        val diceImage1 = findViewById<ImageView>(R.id.dice_image)
        val diceImage2 = findViewById<ImageView>(R.id.dice_image4)
        val diceImage3 = findViewById<ImageView>(R.id.dice_image5)
        val diceImage4 = findViewById<ImageView>(R.id.dice_image6)
        val diceImage5 = findViewById<ImageView>(R.id.dice_image7)
        val diceImage6 = findViewById<ImageView>(R.id.dice_image8)
        val diceImage7 = findViewById<ImageView>(R.id.dice_image9)
        val diceImage8 = findViewById<ImageView>(R.id.dice_image10)
        val diceImage9 = findViewById<ImageView>(R.id.dice_image11)
        val diceImage10 = findViewById<ImageView>(R.id.dice_image12)

        if (!this::mediaPlayer.isInitialized) {//mediaplayer for dice rolling sound
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dice)

        }
//diceimage 1 used as click listener to keep dice
        diceImage1.setOnClickListener {
            selectDie(diceImage1, choose[0])
            choose[0] = !choose[0]
        }
        //diceimage 2 used as click listener to keep dice
        diceImage2.setOnClickListener {
            selectDie(diceImage2, choose[1])
            choose[1] = !choose[1]
        }
        //diceimage 3 used as click listener to keep dice
        diceImage3.setOnClickListener {
            selectDie(diceImage3, choose[2])
            choose[2] = !choose[2]
        }
        //diceimage 4 used as click listener to keep dice
        diceImage4.setOnClickListener {
            selectDie(diceImage4, choose[3])
            choose[3] = !choose[3]
        }
        //diceimage 5 used as click listener to keep dice
        diceImage5.setOnClickListener {

            selectDie(diceImage5, choose[4])
            choose[4] = !choose[4]
        }

       // visibility of dices hidden until thrown
        diceImage1.isVisible = false
        diceImage2.isVisible = false
        diceImage3.isVisible = false
        diceImage4.isVisible = false
        diceImage5.isVisible = false
        diceImage6.isVisible = false
        diceImage7.isVisible = false
        diceImage8.isVisible = false
        diceImage9.isVisible = false
        diceImage10.isVisible = false

//score button is used to get the score of the dices
        val scoreButton: Button = findViewById(R.id.score_button)
        scoreButton.text = getString(R.string.Score)
        scoreButton.setOnClickListener {
            if (playerscore > Numberinput.text.toString()
                    .toInt() && computerscore < Numberinput.text.toString().toInt()
            ) {
                reroll()//reroll is used to make game unplayable after the computer or human won
            } else if (computerscore > Numberinput.text.toString()
                    .toInt() && playerscore < Numberinput.text.toString().toInt()
            ) {
                reroll()//reroll is used to make game unplayable after the computer or human won
            } else (press == 1 && count >= 3)
            instance(
                rInt1, rInt2, rInt3, rInt4,
                rInt5, rInt6, rInt7, rInt8, rInt9, rInt10
            //instance is when the human scores instantly rather than taking reroll
            )
        }
        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.text = getString(R.string.Throw)
        rollButton.setOnClickListener {
            val hardSwitch = findViewById<Switch>(R.id.switch1)


            hardSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){//hardswitch button used to controll dificulty

                    Toast.makeText(this,"Dificulty set to Hard",Toast.LENGTH_SHORT).show()

                }


            }
            val rerollbutton: Button = findViewById(R.id.reroll_button)
            rerollbutton.alpha = 0.0f

            val okbutton: Button = findViewById(R.id.ok_button)//ok button is to enter the target value by user
            if (count == 0)
                winScore()
            okbutton.isVisible = false
            Numberinput.isVisible = false
            EnterNum.isVisible = false
            diceImage1.isVisible = true
//dice animation used for all dice
            diceImage1.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage2.isVisible = true
            diceImage2.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage3.isVisible = true
            diceImage3.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage4.isVisible = true
            diceImage4.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage5.isVisible = true
            diceImage5.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage6.isVisible = true
            diceImage6.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage7.isVisible = true
            diceImage7.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage8.isVisible = true
            diceImage8.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage9.isVisible = true
            diceImage9.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()
            diceImage10.isVisible = true
            diceImage10.animate().apply {
                duration = 150

                rotationBy(360f)

            }.start()

            //9th question starts here if the dice score is a tie dices automatcally rolls without a reroll until tie is broken
            if (playerscore > Numberinput.text.toString()
                    .toInt() && computerscore < Numberinput.text.toString().toInt()
            ) {

                reroll()
            } else if (computerscore > Numberinput.text.toString()
                    .toInt() && playerscore < Numberinput.text.toString().toInt()
            ) {
                reroll()
            } else
                mediaPlayer.start()
                rollDice()


            if (playerscore > Numberinput.text.toString()
                    .toInt() && playerscore == computerscore
            ) {//if score is a tie and also has passed the winning score then dies roll auto until tie broken

                totscore()
            } else if (playerscore == computerscore && computerscore != 0) {//if score is a tie then dies roll auto until tie broken

                totscore()
            }
            count++
            if (count % 3 == 0 && count>=3)//this 4th question for rerolls

                totscore()




            //dice scoring auto after all rerolls
        }
        diceText = findViewById(R.id.dice_text)


        val WinText2= findViewById<TextView>(R.id.CWin)
        WinText2.text = computerWins.toString()
        val WinText = findViewById<TextView>(R.id.HWin)
        WinText.text = playerWins.toString()

        Numberinput = findViewById(R.id.Numberinput)
        val okbutton: Button = findViewById(R.id.ok_button)
        okbutton.text = getString(R.string.OK)
        okbutton.setOnClickListener {
            okbutton.isVisible = false
            Numberinput.isVisible = false
            EnterNum.isVisible = false
            winScore()
        }
        Numberinput.text.toString().toInt()
        EnterNum = findViewById<TextView>(R.id.textView4)

        val rerollbutton: Button = findViewById(R.id.reroll_button)

        rerollbutton.text = getString(R.string.Reset)
        rerollbutton.setOnClickListener {

            reroll()
        }
        val helpbutton: FloatingActionButton = findViewById(R.id.help)//help button used for user to see rules of game
        helpbutton.setOnClickListener {
            help()
        }
    }


    fun selectDie(imageView: ImageView, boolean: Boolean) {//this is to select witch dice to keep with color effect
        if (!boolean) {
            imageView.setBackgroundColor(Color.CYAN)
            imageView.setPadding(10, 10, 10, 10)
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT)
            imageView.setPadding(0, 0, 0, 0)
        }
    }

    private fun requireContext(): Context {

        return this

    }

    @SuppressLint("InflateParams")
    fun totscore() {//function used to get score of roll
            var randomInt1 = Random().nextInt(6) + 1
            //first dice
            var randomInt2 = Random().nextInt(6) + 1
            //second dice
            var randomInt3 = Random().nextInt(6) + 1
            //third dice
            var randomInt4 = Random().nextInt(6) + 1
            //fourth dice
            var randomInt5 = Random().nextInt(6) + 1
        //fifth
            //computer dice roll //computer first dice
            var randomInt6 = Random().nextInt(6) + 1
            //computer second dice
            var randomInt7 = Random().nextInt(6) + 1
            //computer third dice
            var randomInt8 = Random().nextInt(6) + 1
            //computer forth dice
            var randomInt9 = Random().nextInt(6) + 1
            //computer fifth dice
            var randomInt10 = Random().nextInt(6) + 1


            for (i in 0..4) {
                if (!choose[0]) {
                    val drawableResource1 = when (randomInt1) {
                        1 -> R.drawable.die_face_1
                        2 -> R.drawable.die_face_2
                        3 -> R.drawable.die_face_3
                        4 -> R.drawable.die_face_4
                        5 -> R.drawable.die_face_5
                        else -> R.drawable.die_face_6
                    }
                    val diceImage1 = findViewById<ImageView>(R.id.dice_image)
                    diceImage1.setImageResource(drawableResource1)


                }
            }
            for (i in 0..4) {
                if (!choose[1]) {
                    val drawableResource2 = when (randomInt2) {
                        1 -> R.drawable.die_face_1
                        2 -> R.drawable.die_face_2
                        3 -> R.drawable.die_face_3
                        4 -> R.drawable.die_face_4
                        5 -> R.drawable.die_face_5
                        else -> R.drawable.die_face_6
                    }
                    val diceImage2 = findViewById<ImageView>(R.id.dice_image4)
                    diceImage2.setImageResource(drawableResource2)


                }
            }
            for (i in 0..4) {
                if (!choose[2]) {
                    val drawableResource3 = when (randomInt3) {
                        1 -> R.drawable.die_face_1
                        2 -> R.drawable.die_face_2
                        3 -> R.drawable.die_face_3
                        4 -> R.drawable.die_face_4
                        5 -> R.drawable.die_face_5
                        else -> R.drawable.die_face_6
                    }
                    val diceImage3 = findViewById<ImageView>(R.id.dice_image5)
                    diceImage3.setImageResource(drawableResource3)


                }
            }
            for (i in 0..4) {
                if (!choose[3]) {
                    val drawableResource4 = when (randomInt4) {
                        1 -> R.drawable.die_face_1
                        2 -> R.drawable.die_face_2
                        3 -> R.drawable.die_face_3
                        4 -> R.drawable.die_face_4
                        5 -> R.drawable.die_face_5
                        else -> R.drawable.die_face_6
                    }
                    val diceImage4 = findViewById<ImageView>(R.id.dice_image6)
                    diceImage4.setImageResource(drawableResource4)


                }
            }
            for (i in 0..4) {//until dice 5 which are the human dices a differebt method is used her because the user can pick dices
                if (!choose[4]) {
                    val drawableResource5 = when (randomInt5) {
                        1 -> R.drawable.die_face_1
                        2 -> R.drawable.die_face_2
                        3 -> R.drawable.die_face_3
                        4 -> R.drawable.die_face_4
                        5 -> R.drawable.die_face_5
                        else -> R.drawable.die_face_6
                    }
                    val diceImage5 = findViewById<ImageView>(R.id.dice_image7)
                    diceImage5.setImageResource(drawableResource5)


                }
            }
            val drawableResource6 = when (randomInt6) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }
            val diceImage6 = findViewById<ImageView>(R.id.dice_image8)
            diceImage6.setImageResource(drawableResource6)

            //computer second dice


            val drawableResource7 = when (randomInt7) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }
            val diceImage7 = findViewById<ImageView>(R.id.dice_image9)
            diceImage7.setImageResource(drawableResource7)

            //computer third dice

            val drawableResource8 = when (randomInt8) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }
            val diceImage8 = findViewById<ImageView>(R.id.dice_image10)
            diceImage8.setImageResource(drawableResource8)

            //computer forth dice

            val drawableResource9 = when (randomInt9) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }
            val diceImage9 = findViewById<ImageView>(R.id.dice_image11)
            diceImage9.setImageResource(drawableResource9)

            //computer fifth dice


            val drawableResource10 = when (randomInt10) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }
            val diceImage10 = findViewById<ImageView>(R.id.dice_image12)
            diceImage10.setImageResource(drawableResource10)

//players total score
            val diceText = findViewById<TextView>(R.id.dice_text)
            var player = (randomInt1 + randomInt2 + randomInt3 + randomInt4 + randomInt5)
            playerscore += player
            diceText.text = playerscore.toString()

            //Computer score
            // on left side
            val diceText2 = findViewById<TextView>(R.id.dice_text2)
            var computer = (randomInt10 + randomInt6 + randomInt9 + randomInt8 + randomInt7)
            computerscore += computer
            diceText2.text = computerscore.toString()

        if (playerscore > Numberinput.text.toString()
                .toInt() && computerscore < Numberinput.text.toString().toInt()
        ) {
            val layout = layoutInflater.inflate(R.layout.popup_win, null)//popup window used to show you win
            val width = ConstraintLayout.LayoutParams.MATCH_PARENT
            val height = ConstraintLayout.LayoutParams.MATCH_PARENT
            val popUpWindow = PopupWindow(layout, width, height)
            val scoreButton: Button = findViewById(R.id.score_button)

            scoreButton.setOnClickListener {
                popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 100)

            }

            layout.setOnTouchListener(View.OnTouchListener { _, _ -> popUpWindow.dismiss();true })//when the popup window is touched it disepears
        } else if (computerscore > Numberinput.text.toString()
                .toInt() && playerscore < Numberinput.text.toString().toInt()
        ) {
            val layout = layoutInflater.inflate(R.layout.popup_lose, null)//popup window used to show you lose
            val width = ConstraintLayout.LayoutParams.MATCH_PARENT
            val height = ConstraintLayout.LayoutParams.MATCH_PARENT
            val popUpWindow = PopupWindow(layout, width, height)
            val scoreButton: Button = findViewById(R.id.score_button)

            scoreButton.setOnClickListener {
                popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 100)

            }

            layout.setOnTouchListener(View.OnTouchListener { _, _ -> popUpWindow.dismiss();true })

        }
            /*MaterialAlertDialogBuilder(this)
            .setTitle("New Round!")
            .setMessage("Starts Now")
            .show()*/


            /* val computer = (randomInt10 + randomInt6 + randomInt9 + randomInt8 + randomInt7)
         computerscore += computer + 1*/

    }


    @SuppressLint("SetTextI18n")
     fun rollDice() {


        var rInt6 = Random().nextInt(6)+1
        //computer second dice
        var rInt7 = Random().nextInt(6)+1
        //computer third dice
        var rInt8 = Random().nextInt(6)+1
        //computer forth dice
        var rInt9 = Random().nextInt(6)+1
        //computer fifth dice
        var rInt10 = Random().nextInt(6)+1


        for (i in 0..4){
            if (!choose[0]){
                var rInt1 = java.util.Random().nextInt(6)+1
                val drawableResource1 = when (rInt1) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                var diceImage1 = findViewById<ImageView>(R.id.dice_image)
                diceImage1.setImageResource(drawableResource1)


            }
        }
        for (i in 0..4){
            if (!choose[1]){
                var rInt2 = java.util.Random().nextInt(6)+1
                val drawableResource2 = when (rInt2) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage2 = findViewById<ImageView>(R.id.dice_image4)
                diceImage2.setImageResource(drawableResource2)


            }
        }
        for (i in 0..4){
            if (!choose[2]){
                var rInt3 = java.util.Random().nextInt(6)+1
                val drawableResource3 = when (rInt3) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage3 = findViewById<ImageView>(R.id.dice_image5)
                diceImage3.setImageResource(drawableResource3)



            }
        }
        for (i in 0..4){
            if (!choose[3]){
                var rInt4 = java.util.Random().nextInt(6)+1
                val drawableResource4 = when (rInt4) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage4 = findViewById<ImageView>(R.id.dice_image6)
                diceImage4.setImageResource(drawableResource4)



            }
        }
        for (i in 0..4){
            if (!choose[4]){
                var rInt5 = java.util.Random().nextInt(6)+1
                val drawableResource5 = when (rInt5) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage5 = findViewById<ImageView>(R.id.dice_image7)
                diceImage5.setImageResource(drawableResource5)



            }
        }

        val drawableResource6 = when (rInt6) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage6 = findViewById<ImageView>(R.id.dice_image8)
        diceImage6.setImageResource(drawableResource6)

        //computer second dice


        val drawableResource7 = when (rInt7) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage7 = findViewById<ImageView>(R.id.dice_image9)
        diceImage7.setImageResource(drawableResource7)

        //computer third dice



        val drawableResource8 = when (rInt8) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage8 = findViewById<ImageView>(R.id.dice_image10)
        diceImage8.setImageResource(drawableResource8)

        //computer forth dice

        val drawableResource9 = when (rInt9) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage9 = findViewById<ImageView>(R.id.dice_image11)
        diceImage9.setImageResource(drawableResource9)

        //computer fifth dice


        val drawableResource10 = when (rInt10) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage10 = findViewById<ImageView>(R.id.dice_image12)
        diceImage10.setImageResource(drawableResource10)
     }
    fun instance(randomInt1: Int,randomInt2: Int,randomInt3: Int,randomInt4: Int,
                 randomInt5: Int,randomInt6: Int,randomInt7: Int,randomInt8: Int,randomInt9: Int,randomInt10: Int) {//used to  user to score instanlty at any round

        val diceText = findViewById<TextView>(R.id.dice_text)
        var player = (randomInt1 + randomInt2 + randomInt3 + randomInt4 + randomInt5)
        playerscore += player
        diceText.text = playerscore.toString()


        //Computer score
        // on left side
        val diceText2 = findViewById<TextView>(R.id.dice_text2)
        var computer = (randomInt10 + randomInt6 + randomInt9 + randomInt8 + randomInt7)
        computerscore += computer
        diceText2.text = computerscore.toString()

    }


    private fun winScore() {//the sected nuber by user shown as toast messege
        Toast.makeText(
            this,
            "You have selected Number " + Numberinput.text.toString().toInt() + "",
            Numberinput.text.toString().toInt()
        ).show()
    }

    private fun help() {//the instructions button
        val diceText2 = findViewById<TextView>(R.id.dice_text2)
        MaterialAlertDialogBuilder(this)
            .setTitle("The rules of the game")
            .setMessage(
                "*In the case of a tie the 2 players should keep rolling until\n" +
                        "the tie is broken - no re-rolls in this case\n" +
                        "_____________________________________________\n" +

                        "After a roll, each player may either score or take up to two optional re-rolls.\n" +
                        "For each re-roll,\n" +
                        "you may re-roll all of the dice or select any of the dice to keep and only re-roll the remainder.\n" +
                        "* If you want to Re-roll Only 1 Dice Select Number.\n" +
                        "* If you want Re-roll All Dice, Select All Button\n" +
                        "\n" +
                        "The Current Score is\n" +
                        "Computer: " + diceText2.text + "    Player: " + diceText.text
            )
            .show()
    }


    private fun reroll() {//reroll is used when game is over to hide all button and also to make unusable

        val okbutton: Button = findViewById(R.id.ok_button)
        okbutton.isVisible=false
        Numberinput = findViewById(R.id.Numberinput)
        Numberinput.isVisible = false
        val rerollbutton: Button = findViewById(R.id.reroll_button)
        rerollbutton.isVisible= false
        val scoreButton: Button = findViewById(R.id.score_button)
        scoreButton.isVisible= false
        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.isVisible=false
        if (playerscore > Numberinput.text.toString()
                .toInt() && computerscore < Numberinput.text.toString().toInt()
        ) {
            val layout = layoutInflater.inflate(R.layout.popup_win,null)
            val width = ConstraintLayout.LayoutParams.MATCH_PARENT
            val height = ConstraintLayout.LayoutParams.MATCH_PARENT
            val popUpWindow = PopupWindow(layout,width, height)
            popUpWindow.showAtLocation(layout,Gravity.CENTER,0,100)
            layout.setOnTouchListener(View.OnTouchListener{ _, _ ->popUpWindow.dismiss();true})
        } else if (computerscore > Numberinput.text.toString()
                .toInt() && playerscore < Numberinput.text.toString().toInt()
        ) {
            val layout = layoutInflater.inflate(R.layout.popup_lose,null)
            val width = ConstraintLayout.LayoutParams.MATCH_PARENT
            val height = ConstraintLayout.LayoutParams.MATCH_PARENT
            val popUpWindow = PopupWindow(layout,width, height)
            popUpWindow.showAtLocation(layout,Gravity.CENTER,0,100)
            layout.setOnTouchListener(View.OnTouchListener{ _, _ ->popUpWindow.dismiss();true})

        }




    }
    private fun hard() {//this is used for 12 question
        var randomInt1 = Random().nextInt(6) + 1
        //first dice
        var randomInt2 = Random().nextInt(6) + 1
        //second dice
        var randomInt3 = Random().nextInt(6) + 1
        //third dice
        var randomInt4 = Random().nextInt(6) + 1
        //fourth dice
        var randomInt5 = Random().nextInt(6) + 1
        //fifth
        //computer dice roll //computer first dice
        var randomInt6 = Random().nextInt(6) + 1
        //computer second dice
        var randomInt7 = Random().nextInt(6) + 1
        //computer third dice
        var randomInt8 = Random().nextInt(6) + 1
        //computer forth dice
        var randomInt9 = Random().nextInt(6) + 1
        //computer fifth dice
        var randomInt10 = Random().nextInt(6) + 1

// this methoid is used to change stratergy of computer only if computer  get dices 1,2,3 it will reroll dices or else he will keep the dices if its above 3
        //so that the computer always gets high scores after all its rerolls, this makes it harder for user to win
        for (i in 0..4) {
            if (!choose[0]) {
                val drawableResource1 = when (randomInt1) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage1 = findViewById<ImageView>(R.id.dice_image)
                diceImage1.setImageResource(drawableResource1)


            }
        }
        for (i in 0..4) {
            if (!choose[1]) {
                val drawableResource2 = when (randomInt2) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage2 = findViewById<ImageView>(R.id.dice_image4)
                diceImage2.setImageResource(drawableResource2)


            }
        }
        for (i in 0..4) {
            if (!choose[2]) {
                val drawableResource3 = when (randomInt3) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage3 = findViewById<ImageView>(R.id.dice_image5)
                diceImage3.setImageResource(drawableResource3)


            }
        }
        for (i in 0..4) {
            if (!choose[3]) {
                val drawableResource4 = when (randomInt4) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage4 = findViewById<ImageView>(R.id.dice_image6)
                diceImage4.setImageResource(drawableResource4)


            }
        }
        for (i in 0..4) {
            if (!choose[4]) {
                val drawableResource5 = when (randomInt5) {
                    1 -> R.drawable.die_face_1
                    2 -> R.drawable.die_face_2
                    3 -> R.drawable.die_face_3
                    4 -> R.drawable.die_face_4
                    5 -> R.drawable.die_face_5
                    else -> R.drawable.die_face_6
                }
                val diceImage5 = findViewById<ImageView>(R.id.dice_image7)
                diceImage5.setImageResource(drawableResource5)


            }
        }
        val drawableResource6 = when (randomInt6) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage6 = findViewById<ImageView>(R.id.dice_image8)
        diceImage6.setImageResource(drawableResource6)

        //computer second dice


        val drawableResource7 = when (randomInt7) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage7 = findViewById<ImageView>(R.id.dice_image9)
        diceImage7.setImageResource(drawableResource7)

        //computer third dice

        val drawableResource8 = when (randomInt8) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage8 = findViewById<ImageView>(R.id.dice_image10)
        diceImage8.setImageResource(drawableResource8)

        //computer forth dice

        val drawableResource9 = when (randomInt9) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage9 = findViewById<ImageView>(R.id.dice_image11)
        diceImage9.setImageResource(drawableResource9)

        //computer fifth dice


        val drawableResource10 = when (randomInt10) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        val diceImage10 = findViewById<ImageView>(R.id.dice_image12)
        diceImage10.setImageResource(drawableResource10)

//players total score
        val diceText = findViewById<TextView>(R.id.dice_text)
        var player = (randomInt1 + randomInt2 + randomInt3 + randomInt4 + randomInt5)
        playerscore += player
        diceText.text = playerscore.toString()


        //Computer score
        // on left side
        val diceText2 = findViewById<TextView>(R.id.dice_text2)
        if (randomInt10 > 3) {

            computerscore += computer
        } else{

            computerscore += computer-randomInt10
        }
        if (randomInt9>3){

            computerscore += computer

        }else{

            computerscore += computer-randomInt9
        }
        if (randomInt8>3){

            computerscore += computer

        }else{

            computerscore += computer-randomInt8
        }
        if (randomInt7>3){

            computerscore += computer

        }else{

            computerscore += computer-randomInt7
        }
        if (randomInt6>3){

            computerscore += computer

        }else{

            computerscore += computer-randomInt6
        }


        var computer = (randomInt10 + randomInt6 + randomInt9 + randomInt8 + randomInt7)
        computerscore += computer
        diceText2.text = computerscore.toString()

        if (playerscore > Numberinput.text.toString()
                .toInt() && computerscore < Numberinput.text.toString().toInt()
        ) {
            val layout = layoutInflater.inflate(R.layout.popup_win, null)
            val width = ConstraintLayout.LayoutParams.MATCH_PARENT
            val height = ConstraintLayout.LayoutParams.MATCH_PARENT
            val popUpWindow = PopupWindow(layout, width, height)
            val scoreButton: Button = findViewById(R.id.score_button)

            scoreButton.setOnClickListener {
                popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 100)

            }
            layout.setOnTouchListener(View.OnTouchListener { _, _ -> popUpWindow.dismiss();true })
        } else if (computerscore > Numberinput.text.toString()
                .toInt() && playerscore < Numberinput.text.toString().toInt()
        ) {
            val layout = layoutInflater.inflate(R.layout.popup_lose, null)
            val width = ConstraintLayout.LayoutParams.MATCH_PARENT
            val height = ConstraintLayout.LayoutParams.MATCH_PARENT
            val popUpWindow = PopupWindow(layout, width, height)
            val scoreButton: Button = findViewById(R.id.score_button)

            scoreButton.setOnClickListener {
                popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 100)

            }
            layout.setOnTouchListener(View.OnTouchListener { _, _ -> popUpWindow.dismiss();true })

        }

    }


    @Deprecated("")
    override fun onBackPressed() {//on back press the goes back to main page without crashing

        finishAffinity()
        val Intent = Intent(this, MainActivity::class.java)
        startActivity(Intent)


    }

    override fun onPause() {// used shared preferences to get user and computer win of previous round
        super.onPause()
        val editor= sharedPreferences.edit()
        editor.putInt("userWins",playerWins)
        editor.putInt("compWins",computerWins)

        editor.apply()
    }


//for the 13 part when app is rotated to lanscape
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val player = playerscore
        val computer = computerscore
        val playerW = playerWins
        val computerW = computerWins
        var randomInt1 = Random().nextInt(6) + 1
        //first dice
        var randomInt2 = Random().nextInt(6) + 1
        //second dice
        var randomInt3 = Random().nextInt(6) + 1
        //third dice
        var randomInt4 = Random().nextInt(6) + 1
        //fourth dice
        var randomInt5 = Random().nextInt(6) + 1
        //fifth
        //computer dice roll //computer first dice
        var randomInt6 = Random().nextInt(6) + 1
        //computer second dice
        var randomInt7 = Random().nextInt(6) + 1
        //computer third dice
        var randomInt8 = Random().nextInt(6) + 1
        //computer forth dice
        var randomInt9 = Random().nextInt(6) + 1
        //computer fifth dice
        var randomInt10 = Random().nextInt(6) + 1

        var ranPlay = intArrayOf(randomInt1,randomInt2,randomInt3,randomInt4,randomInt5)
        var comPlay = intArrayOf(randomInt6,randomInt7,randomInt8,randomInt9,randomInt10)

        outState.putInt("player",playerscore)
        outState.putInt("computer",computerscore)
        outState.putInt("playerW",playerWins)
        outState.putInt("computerW",computerWins)
        outState.putBoolean("Choose1",choose[0])
        outState.putBoolean("Choose1",choose[1])
        outState.putBoolean("Choose1",choose[2])
        outState.putBoolean("Choose1",choose[3])
        outState.putBoolean("Choose1",choose[4])
        outState.putIntArray("curentPlayer", ranPlay)
        outState.putIntArray("curentComp", comPlay)

        super.onSaveInstanceState(outState)



    }

    //for the 13 part when app is rotated to lanscape
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)


        val playerInt = savedInstanceState.getInt("player",0)
        val CompInt = savedInstanceState.getInt("computer",0)
        val playerW = savedInstanceState.getInt("playerW",0)
        val compW = savedInstanceState.getInt("computerW",0)
        super.onRestoreInstanceState(savedInstanceState)
        ranPlay = savedInstanceState.getIntArray("curentPlayer")!!
        comPlay= savedInstanceState.getIntArray("curentComp")!!
        choose[0] = savedInstanceState.getBoolean("Choose1")
        choose[1] = savedInstanceState.getBoolean("Choose2")
        choose[2] = savedInstanceState.getBoolean("Choose3")
        choose[3] = savedInstanceState.getBoolean("Choose4")
        choose[4] = savedInstanceState.getBoolean("Choose5")
        for (i in 0..4){
            val diceImage1 = findViewById<ImageView>(R.id.dice_image)
            val diceImage2 = findViewById<ImageView>(R.id.dice_image4)
            val diceImage3 = findViewById<ImageView>(R.id.dice_image5)
            val diceImage4 = findViewById<ImageView>(R.id.dice_image6)
            val diceImage5 = findViewById<ImageView>(R.id.dice_image7)
            val diceImage6 = findViewById<ImageView>(R.id.dice_image8)
            val diceImage7 = findViewById<ImageView>(R.id.dice_image9)
            val diceImage8 = findViewById<ImageView>(R.id.dice_image10)
            val diceImage9 = findViewById<ImageView>(R.id.dice_image11)
            val diceImage10 = findViewById<ImageView>(R.id.dice_image12)

            val drawableResource = when (ranPlay[i]) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }
            val drawableResource2 = when (comPlay[i]) {
                1 -> R.drawable.die_face_1
                2 -> R.drawable.die_face_2
                3 -> R.drawable.die_face_3
                4 -> R.drawable.die_face_4
                5 -> R.drawable.die_face_5
                else -> R.drawable.die_face_6
            }


            var playerImg= arrayOf(diceImage1,diceImage2 ,diceImage3,diceImage4,diceImage5)
            var computerImg= arrayOf(diceImage6,diceImage7 ,diceImage8,diceImage9,diceImage10)
            if (ranPlay[i]!=0)
                playerImg[i].setImageResource(drawableResource)

            if (comPlay[i]!=0)
                computerImg[i].setImageResource(drawableResource2)

        }
        playerscore=playerInt
        computerscore=CompInt



        diceText.text = playerscore.toString()

        val diceText2 = findViewById<TextView>(R.id.dice_text2)
        diceText2.text= computerscore.toString()


    }


}


    /*private fun Win(){
        val rand = java.util.Random().nextInt(100) + 1
        val Hwin = findViewById<TextView>(R.id.dice_text)
        val H = (rand )
        Win += H + 1
        Hwin.text = Win.toString()

        Win++
        if (Win<=3)
            roll1()
        if (Win>3)
            warn()
    }*/
    /*private fun warn(){
        val diceText2 = findViewById<TextView>(R.id.dice_text2)
        MaterialAlertDialogBuilder(this)
            .setTitle("Attention\n" +
                    "Reached Max Number of 3 Re-rolls")
            .setMessage("Current Score: \n" +
                    "Steve: "+ diceText2.text +"  You: "+ diceText.text)
            .show()*/










