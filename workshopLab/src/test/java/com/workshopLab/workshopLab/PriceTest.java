package com.workshopLab.workshopLab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshopLab.workshopLab.model.Prices;
import com.workshopLab.workshopLab.repository.PricesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.NestedServletException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(BookController.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricesRepository mockRepository;

    @Before
    public void init() {
        Prices price = new Prices(13l, "Repair the faucet", "All additional costs are not included", 500.0);
        when(mockRepository.findById(13L)).thenReturn(Optional.of(price));
    }

    @Test
    public void find_PriceId_OK() throws Exception {

        mockMvc.perform(get("/prices/13"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(13)))
                .andExpect(jsonPath("$.name", is("Repair the faucet")))
                .andExpect(jsonPath("$.description", is("All additional costs are not included")))
                .andExpect(jsonPath("$.price", is(500.0)));

        verify(mockRepository, times(1)).findById(13L);

    }

    @Test
    public void find_allPrice_OK() throws Exception {

        List<Prices> prices = Arrays.asList(
                new Prices(13l, "Repair the faucet", "All additional costs are not included", 500.0),
                new Prices(14l, "Adjustment of windows and doors", "the price depends on the complexity ", 350.0),
                new Prices(15L, "Replacement of the lock", "", 400.0),
                new Prices(16l, "Consultation", "", 0.0));

        when(mockRepository.findAll()).thenReturn(prices);

        mockMvc.perform(get("/prices"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(13)))
                .andExpect(jsonPath("$[1].id", is(14)))
                .andExpect(jsonPath("$[2].id", is(15)))
                .andExpect(jsonPath("$[3].id", is(16)));


        verify(mockRepository, times(1)).findAll();
    }
    @Test(expected = NestedServletException.class)
    public void find_priceIdNotFound_404() throws Exception {
        mockMvc.perform(get("/prices/5")).andExpect(status().isNotFound());
    }


    @Test
    public void save_price_OK() throws Exception {

        Prices newBook = new Prices(17l, "Spring Boot Guide", "test", 2.99);
        when(mockRepository.save(any(Prices.class))).thenReturn(newBook);

        mockMvc.perform(post("/prices")
                .content(om.writeValueAsString(newBook))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(17)))
                .andExpect(jsonPath("$.name", is("Spring Boot Guide")))
                .andExpect(jsonPath("$.description", is("test")))
                .andExpect(jsonPath("$.price", is(2.99)));

        verify(mockRepository, times(1)).save(any(Prices.class));

    }
    @Test
    public void update_price_OK() throws Exception {

        Prices priceUpdate = new Prices(13l, "Repair the faucet", "All additional costs are not included", 600.0);
        when(mockRepository.save(any(Prices.class))).thenReturn(priceUpdate);

        mockMvc.perform(put("/prices/13")
                .content(om.writeValueAsString(priceUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(13)))
                .andExpect(jsonPath("$.name", is("Repair the faucet")))
                .andExpect(jsonPath("$.description", is("All additional costs are not included")))
                .andExpect(jsonPath("$.price", is(600.0)));


    }
    @Test
    public void delete_price_OK() throws Exception {

        doNothing().when(mockRepository).deleteById(13L);

        mockMvc.perform(delete("/prices/13"))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).deleteById(13L);
    }
    @Test
    public void find_priceDeleteIdNotFound_404() throws Exception {
        mockMvc.perform(delete("/prices/5")).andExpect(status().isNotFound());
    }


}