package com.scratchgame.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlainBigDecimalSerializerTest {

    @Mock
    private JsonGenerator jsonGenerator;
    @Mock
    private SerializerProvider serializerProvider;
    @InjectMocks
    private PlainBigDecimalSerializer serializer;

    @Test
    void serialize_ShouldWritePlainNumberWithoutTrailingZeros_WhenValueHasTrailingZeros() throws IOException {
        BigDecimal value = new BigDecimal("1234.50000");

        serializer.serialize(value, jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeNumber("1234.5");
    }

    @Test
    void serialize_ShouldWritePlainNumber_WhenValueIsAnInteger() throws IOException {
        BigDecimal value = new BigDecimal("1000");

        serializer.serialize(value, jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeNumber("1000");
    }

    @Test
    void serialize_ShouldWritePlainNumberInScientificNotation_WhenValueIsVeryLarge() throws IOException {
        BigDecimal value = new BigDecimal("1E+10");

        serializer.serialize(value, jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeNumber("10000000000");
    }

    @Test
    void serialize_ShouldWritePlainNumber_WhenValueIsZero() throws IOException {
        BigDecimal value = new BigDecimal("0.00000");

        serializer.serialize(value, jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeNumber("0");
    }
}
