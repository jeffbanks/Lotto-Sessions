package com.jjbanks.lotto.model
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
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

/**
 * Test coverage for Power Drawing use cases.
 */
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

        when:
        def powerDrawing = new PowerDrawing(date, corePicks, powerPick)
        then:
        powerDrawing
        powerDrawing.getCorePicks()
        powerDrawing.getDate()
        powerDrawing.getPowerPick()

        and:

        when:
        powerDrawing.validate()
        then:
        noExceptionThrown()
    }

    @Unroll
    def "power drawing - validation picks failure: #msg"() {

        given:
        LocalDate date = LocalDate.of(2017, 10, 1)

        when:
        Integer powerPick = pp1
        Set<Integer> corePicks = new HashSet()
        corePicks.add(cp1)
        corePicks.add(cp2)
        corePicks.add(cp3)
        corePicks.add(cp4)
        corePicks.add(cp5)
        def powerDrawing = new PowerDrawing(date, corePicks, powerPick)
        powerDrawing.validate()
        then:
        thrown(IllegalArgumentException)
        where:
        cp1 | cp2 | cp3 | cp4 | cp5 | pp1 | msg
        2   | 2   | 4   | 5   | 1   | 22  | "duplicate"
        1   | 6   | 2   | 4   | 66  | 36  | "core beyond max"
        1   | 6   | 2   | 4   | 65  | 37  | "power beyond max"
        0   | 6   | 2   | 4   | 65  | 36  | "core below min"
        1   | 6   | 2   | 4   | 65  | 0   | "power below min"

    }


    @Unroll
    def "power drawing - validation success with core picks"() {

        given:
        LocalDate date = LocalDate.of(2017, 10, 1)

        when:
        Integer powerPick = pp1
        Set<Integer> corePicks = new HashSet()
        corePicks.add(cp1)
        corePicks.add(cp2)
        corePicks.add(cp3)
        corePicks.add(cp4)
        corePicks.add(cp5)
        def powerDrawing = new PowerDrawing(date, corePicks, powerPick)
        powerDrawing.validate()
        then:
        noExceptionThrown()
        where:
        cp1 | cp2 | cp3 | cp4 | cp5 | pp1
        1   | 2   | 3   | 4   | 5   | 1
        1   | 6   | 2   | 4   | 65  | 36
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
        when:
        new PowerDrawing(null, corePicks, powerPick)
        then:"exception thrown as date can't be null"
        thrown(IllegalArgumentException)

        and:
        when:
        new PowerDrawing(date, null, powerPick)

        then:"exception thrown as core picks can't be null"
        thrown(IllegalArgumentException)


        // The null case below  for powerpick
        // had to be covered in Java, but checks no longer needed with Kotlin
        // We defined the constructor to require a non-null value for powerPick.
        // Kotlin will detect and identify as problem at compile time!
        // If you wanted to allow, you would provide '?' after the parameter.
        // new PowerDrawing(date, corePicks, null)

    }
}
