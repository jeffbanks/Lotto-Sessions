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

package com.jjbanks.lotto.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.IntStream;

public class PowerDrawing extends Drawing {

    public static final int CORE_PICKS_SIZE = 5;
    private static final int MAX_CORE_PICK = 65;
    private static final int MAX_POWER_PICK = 36;


    private Integer powerPick;

    /**
     * @param date
     * @param corePicks
     * @param powerPick
     */
    public PowerDrawing(LocalDate date, Set<Integer> corePicks, Integer powerPick) {
        super(date, corePicks);

        if (powerPick == null) {
            throw new IllegalArgumentException("Missing power pick for drawing");
        }
        this.powerPick = powerPick;
    }

    /**
     * @return single power pick
     */
    public Integer getPowerPick() {

        return powerPick;
    }

    /**
     * @throws FailedValidationException
     */
    @Override
    void validate() throws FailedValidationException {

        validateCorePicksSize(CORE_PICKS_SIZE);

        boolean corePicksInRange = corePicks.stream()
                .allMatch(x -> IntStream.rangeClosed(1, MAX_CORE_PICK).anyMatch(r -> r == x));

        if (!corePicksInRange) {
            throw new FailedValidationException("Core picks not in acceptable range 1 ... " + MAX_CORE_PICK);
        }

        boolean powerPickInRange = IntStream.rangeClosed(1, MAX_POWER_PICK).anyMatch(r -> r == powerPick);
        if (!powerPickInRange) {
            throw new FailedValidationException("Power pick not in acceptable range 1 ... " + MAX_POWER_PICK);
        }
    }

    @Override
    public String toString() {
        return "PowerDrawing{" +
                "date=" + date +
                ", corePicks=" + corePicks +
                ", powerPick=" + powerPick +
                '}';
    }
}
