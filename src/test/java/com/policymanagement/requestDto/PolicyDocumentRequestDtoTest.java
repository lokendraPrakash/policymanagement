package com.policymanagement.requestDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PolicyDocumentRequestDtoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        PolicyDocumentRequestDto dto = new PolicyDocumentRequestDto();
        dto.setTitle("My Policy");
        dto.setAuthor("Lokendra");
        dto.setType("pdf");

        assertEquals("My Policy", dto.getTitle());
        assertEquals("Lokendra", dto.getAuthor());
        assertEquals("pdf", dto.getType());
    }

    @Test
    void testAllArgsConstructor() {
        PolicyDocumentRequestDto dto = new PolicyDocumentRequestDto("Health Policy", "Ravi", "txt");

        assertEquals("Health Policy", dto.getTitle());
        assertEquals("Ravi", dto.getAuthor());
        assertEquals("txt", dto.getType());
    }
}
