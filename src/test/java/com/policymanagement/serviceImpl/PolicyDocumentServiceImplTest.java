package com.policymanagement.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.policymanagement.entity.PolicyDocument;
import com.policymanagement.exceptionHandler.PolicyManagementException;
import com.policymanagement.repository.PolicyDocumentRepository;
import com.policymanagement.requestDto.PolicyDocumentRequestDto;
import com.policymanagement.responseDto.PolicyDocumentResponseDto;
import com.policymanagement.responseDto.QnaSearchResponseDto;

@ExtendWith(MockitoExtension.class)
class PolicyDocumentServiceImplTest {

	@Mock
	private PolicyDocumentRepository repository;

	@InjectMocks
	private PolicyDocumentServiceImpl service;

	@Mock
	private MultipartFile file;

	private PolicyDocument document;

	@BeforeEach
	void setUp() throws Exception {
		document = PolicyDocument.builder().id(1L).title("LIC").author("Sonu").type("pdf").uploadDate(LocalDate.now())
				.content("This is LIC content").build();
	}

	@Test
	void testUploadDocument_Success() throws Exception {
		PolicyDocumentRequestDto requestDto = new PolicyDocumentRequestDto("LIC", "Sonu", "pdf");

		when(file.isEmpty()).thenReturn(false);
		when(file.getSize()).thenReturn(1024L);
		when(file.getInputStream()).thenReturn(new ByteArrayInputStream("This is LIC content".getBytes()));
		when(repository.save(any())).thenReturn(document);

		PolicyDocumentResponseDto response = service.uploadDocument(file, requestDto);

		assertEquals("LIC", response.getTitle());
		assertEquals("Sonu", response.getAuthor());
	}

	@Test
	void testUploadDocument_FileMissing() {
		PolicyDocumentRequestDto requestDto = new PolicyDocumentRequestDto("LIC", "Sonu", "pdf");

		when(file.isEmpty()).thenReturn(true);
		assertThrows(PolicyManagementException.class, () -> service.uploadDocument(file, requestDto));
	}

	@Test
	void testUploadDocument_FileTooLarge() {
		PolicyDocumentRequestDto requestDto = new PolicyDocumentRequestDto("LIC", "Sonu", "pdf");

		when(file.isEmpty()).thenReturn(false);
		when(file.getSize()).thenReturn(20 * 1024 * 1024L);

		assertThrows(PolicyManagementException.class, () -> service.uploadDocument(file, requestDto));
	}

	@Test
	void testSearchDocuments_Found() {
		when(repository.searchByKeyword("lic")).thenReturn(List.of(document));
		List<QnaSearchResponseDto> list = service.searchDocuments("lic");

		assertEquals(1, list.size());
		assertTrue(list.get(0).getSnippet().contains("..."));
	}

	@Test
	void testSearchDocuments_Empty() {
		when(repository.searchByKeyword("dummy")).thenReturn(Collections.emptyList());
		List<QnaSearchResponseDto> result = service.searchDocuments("dummy");

		assertTrue(result.isEmpty());
	}

	@Test
	void testFilterDocuments_Success() {
		when(repository.findByAuthorContainingIgnoreCaseAndTypeContainingIgnoreCase(eq("Sonu"), eq("pdf"), any()))
				.thenReturn(List.of(document));

		List<PolicyDocumentResponseDto> list = service.filterDocuments("Sonu", "pdf", 0, 5);

		assertEquals(1, list.size());
		assertEquals("LIC", list.get(0).getTitle());
	}

}
