package com.example.perceptron

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Perceptron")



        val point: MutableList<MutableList<Double>> = ArrayList()

        button.setOnClickListener {
            val points = checkBoxListener()
            val options = spinnerListener()
            exec(points, options)
        }

    }

    fun exec(points: MutableList<MutableList<Int>>, options: MutableList<Double>) {
        val perceptron: Perceptron = Perceptron(4.0, options[2], points, options[0], options[1])

        perceptron.calc()

        val results = perceptron.get_result()
        val weights = perceptron.get_weights()
        val error = perceptron.error_code()

        weight1.setText(("Weight 1 is " + weights[0].toString()))
        weight2.setText(("Weight 2 is " + weights[1].toString()))
        iterations.setText("Iterations taken " + results[0].toString())
        error_msg.setText(error.toString())
    }

    fun checkBoxListener(): MutableList<MutableList<Int>> {
        val points: MutableList<MutableList<Int>> = ArrayList()

        if (check_A.isChecked) {
            points.add(arrayListOf(0, 6))
        }
        if (check_B.isChecked) {
            points.add(arrayListOf(1, 5))
        }
        if (check_C.isChecked) {
            points.add(arrayListOf(3, 3))
        }
        if (check_D.isChecked) {
            points.add(arrayListOf(2, 4))
        }

        return points
    }

    fun spinnerListener(): MutableList<Double> {
        val options: MutableList<Double> = ArrayList()

        var time = time_lim.selectedItem.toString()
        if (time == "0.5c"){
            options.add(500.0)
        }
        if (time == "1c"){
            options.add(1000.0)
        }
        if (time == "2c"){
            options.add(2000.0)
        }
        if (time == "5c"){
            options.add(5000.0)
        }

        options.add(iter_lim.selectedItem.toString().toDouble())
        options.add(learning_speed.selectedItem.toString().toDouble())

        return options
    }
}
