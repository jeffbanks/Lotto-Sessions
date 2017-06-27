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

public abstract class Drawing {

    protected LocalDate date;
    protected Set<Integer> corePicks;

    abstract void validate() throws FailedValidationException;

    /**
     * @param date      of drawing
     * @param corePicks for drawing
     */
    public Drawing(LocalDate date, Set<Integer> corePicks) {

        if (date == null) {
            throw new IllegalArgumentException("Missing date for drawing");
        }

        if (corePicks == null || corePicks.isEmpty()) {
            throw new IllegalArgumentException("Missing core picks for drawing");
        }

        this.date = date;
        this.corePicks = corePicks;
    }

    /**
     * @return date of drawing
     */
    public LocalDate getDate() {

        return date;
    }

    /**
     * @return set of core picks for drawing
     */
    public Set<Integer> getCorePicks() {
        return corePicks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drawing drawing = (Drawing) o;

        if (!date.equals(drawing.date)) return false;
        return corePicks.equals(drawing.corePicks);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + corePicks.hashCode();
        return result;
    }

    /**
     * @param expectedSize
     * @throws FailedValidationException
     */
    protected void validateCorePicksSize(Integer expectedSize) throws FailedValidationException {

        if (corePicks == null || corePicks.isEmpty() || corePicks.size() != expectedSize) {
            throw new FailedValidationException("Core picks are not the expected size: " + expectedSize);
        }
    }

}
