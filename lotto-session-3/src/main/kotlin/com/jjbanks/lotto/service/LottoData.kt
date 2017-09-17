

/*
 * MIT License
 *
 * Copyright (c) 2017 Jeff Banks
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */
package com.jjbanks.lotto.service
import com.jjbanks.lotto.model.Drawing
import com.jjbanks.lotto.model.PowerDrawing
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class LottoData {

    fun getPowerDrawing(): Drawing {

        // Simulation of drawing retrieval from some source.
        val date: LocalDate = LocalDate.of(2017, 10, 1)
        val powerPick: Int = (1..PowerDrawing.MAX_POWER_PICK).random()

        val corePicks: HashSet<Int> = HashSet()
        for (n in 1..5) {
            while (true) {
                val pick: Int = (1..PowerDrawing.MAX_CORE_PICK).random()
                if (!corePicks.contains(pick)) {
                    corePicks.add(pick)
                    break
                }
            }
        }

        val powerDrawing = PowerDrawing(date, corePicks, powerPick)
        powerDrawing.validate()
        return powerDrawing
    }

    private fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start
}