package com.example.perceptron

import java.util.Random
import kotlin.collections.ArrayList
import kotlin.math.pow

class Perceptron(val target: Double, val speed: Double, val Points: MutableList<MutableList<Int>>, val time_lim: Double, val iter_lim: Double) {
    val random: Random = Random()
    var weight1: Double = random.nextDouble()
    var weight2: Double = random.nextDouble()
    var y: Double = 0.0
    var results: MutableList<Long> = ArrayList()
    var weightRes: MutableList<Double> = ArrayList()
    var error: String = ""


    fun signal(point: MutableList<Int>): Double {
        return point[0]*this.weight1 + point[1]*this.weight2
    }

    fun delta(signal: Double): Double {
        val delta: Double = this.target - signal
        return delta
    }

    fun weights(x1: Int, x2: Int, delta: Double) {
        this.weight1 = this.weight1 + delta*x1*this.speed
        this.weight2 = this.weight2 + delta*x2*this.speed
    }

    fun calc() {
        val start: Long = System.currentTimeMillis()
        var complete_flag: Boolean = false
        var currPoint: MutableList<Int> = this.Points[0]
        var index: Int = 0
        var local_index: Int = 0
        var delta_val: Double
        val time_dl = this.time_lim * 10.0.pow(3)

        while (index++ < iter_lim && (System.currentTimeMillis() - start) < time_dl) {

            local_index = local_index % this.Points.size
            currPoint = this.Points[local_index]

            this.y = signal(currPoint)

            if (valid()) {
                complete_flag = true
                break
            }

            delta_val = delta(y)
            weights(currPoint[0], currPoint[1], delta_val)
        }

        if(!complete_flag) {
            if(index >= iter_lim) {
                this.error = "iter_lim"
                this.results.add(index.toLong())
                this.results.add((System.currentTimeMillis() - start))
            } else {
                this.error = "time_lim"
                this.results.add(index.toLong())
                this.results.add((System.currentTimeMillis() - start))
            }
        } else {
            this.results.add(index.toLong())
            this.results.add((System.currentTimeMillis() - start))
        }
    }

    fun valid(): Boolean {
        var flag: Boolean = true
        var hits: Int = 0
        for(point in this.Points) {
            if (signal(point) > this.target && flag) {
                hits++
            }
            if (signal(point) < this.target && !flag) {
                hits++
            }
            flag = !flag
        }
        return (hits == this.Points.size)
    }

    fun get_result(): MutableList<Long> {
        return this.results
    }

    fun get_weights(): MutableList<Double> {
        this.weightRes.add(weight1);
        this.weightRes.add(weight2);
        return this.weightRes;
    }

    fun error_code(): String {
        if (this.error == "time_lim") {
            return "Error, time limit reached"
        }
        else if (this.error == "iter_lim") {
            return "Error, iteration limit reached"
        }
        else if (this.error == "") {
            return ""
        } else return "Unknown error"
    }
}