package com.ahmedsalihh.foreignexchange.controller;

import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;
import com.ahmedsalihh.foreignexchange.service.ExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ExchangeControllerTest {

    @Autowired
    private ExchangeController exchangeController;

    @MockBean
    private ExchangeService exchangeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(exchangeController).build();
    }

    @Test
    void getExchangeRate() throws Exception {
        String from = "usd";
        String to = "try";
        String expectedResult = "6.8614807";
        Mockito.when(exchangeService.getExchangeRate(from, to)).thenReturn(expectedResult);

        mockMvc.perform(get("/exchange/" + from + "/to/" + to).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void convert() throws Exception {
        Conversion conversion = new Conversion(10, "usd", "try");
        Converted converted = new Converted(68.61481f, 1L);

        ObjectMapper obj = new ObjectMapper();
        String json = "";
        try {
            json = obj.writeValueAsString(conversion);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.when(exchangeService.convert(Mockito.any(Conversion.class))).thenReturn(converted);

        mockMvc.perform(post("/exchange/convert").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String jsonRes = mvcResult.getResponse().getContentAsString();
                    String a = JsonPath.parse(jsonRes).read("amount").toString();
                    String b = JsonPath.parse(jsonRes).read("transactionId").toString();
                    Assert.isTrue(a.equals("68.61481"), "");
                    Assert.isTrue(b.equals("1"), "");
                });

    }

    @Test
    void listConversions() throws Exception {
        int pageNo = 0;
        int pageSize = 10;
        Long transactionId = 1L;
        String transactionDate = "2020-07-19";

        Exchange exchange = new Exchange("usd", "try", 10f, 68.61481f, new Date());
        exchange.setId(1L);

        List<Exchange> list = new ArrayList<>();
        list.add(exchange);

        Mockito.when(exchangeService.getExchangeListByTransactionDate(transactionDate, pageNo, pageSize)).thenReturn(list);
        Mockito.when(exchangeService.getExchangeListByTransactionId(transactionId, pageNo, pageSize)).thenReturn(list);
        Mockito.when(exchangeService.getExchangeListByTransactionIdAndTransactionDate(transactionId, transactionDate, pageNo, pageSize)).thenReturn(list);

        mockMvc.perform(get("/exchange/listConversions?transactionDate=" + transactionDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/exchange/listConversions?transactionId=" + transactionId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/exchange/listConversions?transactionId=" + transactionId + "&transactionDate=" + transactionDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}