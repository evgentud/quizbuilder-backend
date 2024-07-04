package dev.tudos.quizbuilder.common.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ObjectMapperConfigurationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class ObjectMapperSerializationTests {
        @Test
        public void shouldSerializeIntAsNumber() throws JsonProcessingException {
            Map<String, Object> object = Map.of("intValue", 42);

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"intValue\":42}", jsonString);
        }

        @Test
        public void shouldSerializeDoubleAsNumber() throws JsonProcessingException {
            Map<String, Object> object = Map.of("doubleValue", 42.42);

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"doubleValue\":42.42}", jsonString);
        }

        @Test
        public void shouldSerializeStringAsString() throws JsonProcessingException {
            Map<String, Object> object = Map.of("stringValue", "string");

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"stringValue\":\"string\"}", jsonString);
        }

        @Test
        public void shouldSerializeBigDecimalAsNumber() throws JsonProcessingException {
            Map<String, Object> object = Map.of("bigDecimal", new BigDecimal("42.42"));

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"bigDecimal\":42.42}", jsonString);
        }

        @Test
        public void shouldSerializeLocalDateAsYyMmDd() throws JsonProcessingException {
            Map<String, Object> object = Map.of("date", LocalDate.of(2021, 1, 1));

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"date\":\"2021-01-01\"}", jsonString);
        }

        @Test
        public void shouldSerializeLocalTimeAsHhhMmSs() throws JsonProcessingException {
            Map<String, Object> object = Map.of("time", LocalTime.of(12, 0));

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"time\":\"12:00:00\"}", jsonString);
        }

        @Test
        public void shouldSerializeLocalDateTimeAsISO() throws JsonProcessingException {
            Map<String, Object> object = Map.of("dateTime", LocalDateTime.of(2021, 12, 31, 12, 59, 59, 999_000_000));

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"dateTime\":\"2021-12-31T12:59:59.999\"}", jsonString);
        }

        @Test
        public void shouldNotIncludeNullValue() throws JsonProcessingException {
            Map<String, Object> object = new HashMap<>();
            object.put("nullValue", null);
            object.put("nonNullValue", "someValue");

            String jsonString = objectMapper.writeValueAsString(object);

            assertEquals("{\"nonNullValue\":\"someValue\"}", jsonString);
        }

        @Test
        public void shouldSerializeObjectWithProperFormatting() throws JsonProcessingException {
            SampleObject sampleObject = new SampleObject();
            sampleObject.setStringField("string");
            sampleObject.setIntField(42);
            sampleObject.setDoubleField(42.42);
            sampleObject.setBigDecimal(new BigDecimal("42.42"));
            sampleObject.setDate(LocalDate.of(2021, 1, 1));
            sampleObject.setTime(LocalTime.of(12, 0));
            sampleObject.setDateTime(LocalDateTime.of(2021, 1, 1, 12, 0, 0, 0));

            String jsonString = objectMapper.writeValueAsString(sampleObject);

            String expected = "{\"string_field\":\"string\",\"int_field\":42,\"double_field\":42.42," +
                    "\"big_decimal\":42.42,\"date\":\"2021-01-01\",\"time\":\"12:00:00\"," +
                    "\"date_time\":\"2021-01-01T12:00:00\"}";
            assertEquals(expected, jsonString);
        }
    }

    @Getter
    @Setter
    private static class SampleObject {
        private String stringField;
        private int intField;
        private double doubleField;
        private String nullValue;
        private BigDecimal bigDecimal;
        private LocalDate date;
        private LocalTime time;
        private LocalDateTime dateTime;
    }
}
