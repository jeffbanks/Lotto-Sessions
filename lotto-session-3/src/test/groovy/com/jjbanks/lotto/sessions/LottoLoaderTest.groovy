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

package com.jjbanks.lotto.sessions

import com.jjbanks.lotto.model.Drawing

import com.jjbanks.lotto.service.LottoData
import com.jjbanks.lotto.service.LottoService
import com.jjbanks.sessions.LottoController
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class LottoLoaderTest extends Specification {

    MockMvc mockMvc
    LottoService mockLottoService

    def setup() {
        mockLottoService = Mock(LottoService)
        mockMvc = standaloneSetup(new LottoController(lottoService: mockLottoService)).build()
    }

    def "load via web service works correctly"() {

        when: "calling web service and get a response"
        def response = mockMvc.perform(get("/").contentType(MediaType.TEXT_PLAIN)).andReturn().response
        def result = response.getContentAsString()

        then: "expect that a valid response occurs and validation of two test drawings were loaded"
        response.getStatus() == 200
        1 * mockLottoService.getDrawings() >> { return buildTestDrawings(2) }
        result == "Successfully loaded.  Number of drawings loaded: 2"
    }

    def "load via web service and unexpected problem occurs"() {

        when: "calling web service and get a response"
        def response = mockMvc.perform(get("/").contentType(MediaType.TEXT_PLAIN)).andReturn().response
        def result = response.getContentAsString()
        println(result)

        then: "expect that exception handling occurred and indicated failure"
        response.getStatus() == 200
        1 * mockLottoService.getDrawings() >> { throw new Exception("Issue occurred getting drawings") }
        result == "Failed to load due to exception: Issue occurred getting drawings"

    }

    /* Test helper to construct N # of drawings */
    def buildTestDrawings(Integer number) {

        LottoData lottoData = new LottoData()
        Set<Drawing> drawings = new HashSet<Drawing>()

        for(Integer i = 0; i<number; i++) {
            drawings.add(lottoData.getPowerDrawing())
        }
        return drawings
    }
}
