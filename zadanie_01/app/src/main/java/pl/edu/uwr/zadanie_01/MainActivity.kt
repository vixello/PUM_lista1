package pl.edu.uwr.zadanie_01

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
const val EXTRA_MESSAGE = "pl.edu.uwr.pum.explicitintentkotlin.MESSAGE"

class Question(
    val question: String,
    val correctAnswer: Int
)
//poprawne: t f tt f t ff tt
object Questions {
    val questions: List<Question>
        get() {
            return listOf(
                Question("Question 1 \n" +
                         "Siłę, która powstaje na styku powierzchni dwóch ciał i przeciwdziała ich względnemu ruchowi, nazywamy siłą tarcia",
                    1),
                Question("Question 2 \n" +
                        "Opór powietrza to siła działająca na siłę wiatru?",
                    0),
                Question("Question 3 \n" +
                        "Tarcie statyczne jest siłą działającą między ciałem spoczywającym na powierzchni, a tą powierzchnią.",
                    1),
                Question("Question 4 \n" +
                        "Zjawisko odrzutu - zjawisko  powstawania siły, zwanej siłą odrzutu, wywołanego tą siłą przyspieszenia oraz wywołanego nim ruchu ciała",
                    1),
                Question("Question 5 \n" +
                        "Gdy siły działające na ciało równoważą się, spełniona jest II Zasada Dynamiki Newtona",
                    0),
                Question("Question 6 \n" +
                        "III Zasada Dynamiki Newtona to inaczej Zasada Akcji i Reakcji ",
                    1),
                Question("Question 7 \n" +
                        "W III Zasadzie Dynamiki, jeżeli działamy na jakiś obiekt pewną siłą to ten obiekt działa na nas taką samą siłą, o takim samym kierunki i tym samym zwrocie",
                    0),
                Question("Question 8 \n" +
                        "Samochód poruszający się ze stałą prędkością jest przykładem Drugiej Zasady Dynamiki Newtona ",
                    0),
                Question("Question 9 \n" +
                        "Jeżeli na dane ciało o masie 50 kg działaby siłą o wartości 300 N, to przyspieszenie tego ciała wyniesie 6 m/s^2",
                    1),
                Question("Question 10\n" +
                        "Na ciało nie działają żadne siły, takie ciało może poruszać się ruchem jednostajnym prostoliniowym",
                    1),
                Question("Quiz zakończony!!",0),
               )
        }


//        get() = (0 until 10).map {
//            }
}

class MainActivity : AppCompatActivity() {
    //correct answer
    private var currentPosition = 0
    private val questions2: List<Question> = Questions.questions

    private var selectedAnswerPosition = 0
    private var count = 0
    private var countCheat = 0
    private var countRightAnswer = 0
    private val questionText: TextView by lazy { findViewById(R.id.show_question) }
    private lateinit var truetext : CharSequence


//    val answers: List<TextView> = listOf(answerOne, answerTwo)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    //hide acction bar
        supportActionBar?.hide()

    //bundle
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("counter")
            currentPosition = savedInstanceState.getInt("counter_state")
            countRightAnswer = savedInstanceState.getInt("counter_answer")
            countCheat = savedInstanceState.getInt("counter_cheat")
            selectedAnswerPosition = savedInstanceState.getInt("selected_answer")
            questionText.text = savedInstanceState.getCharSequence("question_text")
        }

        findViewById<Button>(R.id.search).setOnClickListener{
            val url = "https://www.google.com/search?q=${questions2[currentPosition-2].question.substring(11,questions2[currentPosition-2].question.length)}&"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply{
                addCategory(CATEGORY_BROWSABLE)
            }
            if (intent.resolveActivity(packageManager) != null)
                startActivity(intent)
        }
    setVisibility()
    println(currentPosition)

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", count)
        outState.putInt("counter_state", currentPosition)
        outState.putInt("counter_answer", countRightAnswer)
        outState.putInt("counter_cheat", countCheat)
        outState.putInt("selected_answer", selectedAnswerPosition)
        outState.putCharSequence("question_text",  questionText.text)

    }
    fun setVisibility(){
        //after creating the activity, check what to show
        if(currentPosition == 0) {
            truetext=findViewById<Button>(R.id.true_button).text
            findViewById<Button>(R.id.show_cheat).visibility = View.INVISIBLE
            findViewById<Button>(R.id.false_button).visibility = View.INVISIBLE
            findViewById<Button>(R.id.true_button).text = "Start"
            findViewById<Button>(R.id.true_button).layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            findViewById<Button>(R.id.search).visibility = View.INVISIBLE

        }
        if(currentPosition >= questions2.size) {
            findViewById<Button>(R.id.true_button).text = "Pokaż wynik"
            findViewById<Button>(R.id.true_button).layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            findViewById<Button>(R.id.false_button).visibility = View.INVISIBLE
            findViewById<Button>(R.id.search).visibility = View.INVISIBLE
            findViewById<Button>(R.id.show_cheat).visibility = View.INVISIBLE
        }
    }
    fun answer(view: View) {

        val b = view as Button
        selectedAnswerPosition = if(b.text =="Prawda") {
            1
        } else
            0
        answerQuestion()

    }

    @SuppressLint("SetTextI18n")
    fun answerQuestion(){
        //"$currentPosition / ${10}".also { answerOne.text = it }
        if (currentPosition <= questions2.size && currentPosition>0)
        {
            val question = questions2[currentPosition-2]
            questionText.text = questions2[currentPosition-1].question

            if(selectedAnswerPosition == question.correctAnswer) {
                count += 10
                countRightAnswer +=1
            }
            if(currentPosition  >= questions2.size) {
                findViewById<Button>(R.id.true_button).text = "Pokaż wynik"
                findViewById<Button>(R.id.true_button).layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                findViewById<Button>(R.id.false_button).visibility = View.INVISIBLE
                findViewById<Button>(R.id.search).visibility = View.INVISIBLE
                findViewById<Button>(R.id.show_cheat).visibility = View.INVISIBLE
            }
            currentPosition++

        }
        else if(currentPosition==0)
                {
                    findViewById<Button>(R.id.show_cheat).visibility = View.VISIBLE
                    findViewById<Button>(R.id.false_button).visibility = View.VISIBLE
                    findViewById<Button>(R.id.search).visibility = View.VISIBLE
                    findViewById<Button>(R.id.true_button).text = truetext
                    findViewById<Button>(R.id.true_button).layoutParams.width = findViewById<Button>(R.id.false_button).layoutParams.width
                    questionText.text = questions2[0].question

                    println( questions2[0].correctAnswer)

                    currentPosition+=2
                }

        else {
            Toast.makeText(
                applicationContext,
                "Twój wynik: ${count}/100\nOszukałes $countCheat raz/razy",
                Toast.LENGTH_SHORT
            ).show()
            findViewById<TextView>(R.id.show_question).text = "Poprawne odpowiedzi:\n$countRightAnswer/10"
        }
    }
    fun showCheat(view: View){
        val message = if(questions2[currentPosition-2].correctAnswer == 0)
            "Fałsz"
        else
            "Prawda"

        val intent = Intent(this, SecondActivityy::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        count-=15
        countCheat++

        secondActivityResultLauncher.launch(intent)
    }
    private val secondActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK)
            Toast.makeText(
                applicationContext, "Oszukałeś!! Odjęto 15 pkt",
                Toast.LENGTH_SHORT
            ).show()
/*            findViewById<TextView>(R.id.show_question).text =
                result.data!!.getStringExtra(EXTRA_REPLY)*/
    }
}



