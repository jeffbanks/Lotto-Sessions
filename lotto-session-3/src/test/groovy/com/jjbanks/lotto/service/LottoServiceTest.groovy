package com.jjbanks.lotto.service

import com.jjbanks.lotto.model.Drawing
import com.jjbanks.lotto.service.LottoData
import com.jjbanks.lotto.service.LottoService
import spock.lang.Specification

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

class LottoServiceTest extends Specification {

    LottoService lottoService

    def setup() {
        lottoService = new LottoService()
    }

    def "get drawings with valid lotto data"() {

        given:
        def lottoData = new LottoData()
        lottoService.lottoData = lottoData

        when: "getting expected drawings"
        Set<Drawing> drawings = lottoService.getDrawings()

        then: "expecting that two drawings are returned"
        drawings
        drawings.size() == 2
    }

    def "get drawings with bad lotto data"() {

        given: "mock lotto data referenced by valid lotto service"
        def lottoData = Mock(LottoData)
        lottoService.lottoData = lottoData

        when: "having expected bad lotto data and get drawings"
        lottoData.getPowerDrawing() >> { throw new IllegalArgumentException("Core picks not in acceptable range") }
        lottoService.getDrawings()

        then: "expected exception occurs"
        IllegalArgumentException iae = thrown()
        iae.message == "Core picks not in acceptable range"

    }
}
