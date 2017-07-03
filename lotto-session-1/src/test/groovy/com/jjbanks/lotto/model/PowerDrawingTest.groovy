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

package com.jjbanks.lotto.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class PowerDrawingTest extends Specification {

    void setup() {
    }

    def "power drawing - construct and validation success"() {

        given: "local date, power pick, and core picks defined"
        LocalDate date = LocalDate.of(2017, 10, 1)
        Integer powerPick = 1
        Set<Integer> corePicks = new HashSet()
        corePicks.add(1)
        corePicks.add(2)
        corePicks.add(3)
        corePicks.add(4)
        corePicks.add(5)
        when:"power drawing is constructed with date, core picks, and power pick"
        def powerDrawing = new PowerDrawing(date, corePicks, powerPick)
        then: "power drawing is constructed as expected"
        powerDrawing
        powerDrawing.getCorePicks()
        powerDrawing.getDate()
        powerDrawing.getPowerPick()

        and:

        when: "validation occurs"
        powerDrawing.validate()
        then: "no exception occurs"
        noExceptionThrown()
    }


    def "power drawing - bad arguments"() {

        given:
        LocalDate date = LocalDate.of(2017, 10, 1)
        Integer powerPick = 1
        Set<Integer> corePicks = new HashSet()
        corePicks.add(1)
        corePicks.add(2)
        corePicks.add(3)
        corePicks.add(4)
        corePicks.add(5)
        
        when: "date of drawing is not provided"
        def powerDrawing = new PowerDrawing(null, corePicks, powerPick)
        then:
        thrown(IllegalArgumentException)

        and:

        when:"core picks are not provided"
        def powerDrawing2 = new PowerDrawing(date, null, powerPick)
        then:
        thrown(IllegalArgumentException)

        and:

        when:"power pick is not provided"
        def powerDrawing3 = new PowerDrawing(date, corePicks, null)
        then:
        thrown(IllegalArgumentException)
    }


}
