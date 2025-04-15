package com.policymanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.policymanagement.enumuration.ResultCode;
import com.policymanagement.responseDto.PolicyDocumentResponseDto;
import com.policymanagement.responseDto.QnaSearchResponseDto;
import com.policymanagement.service.PolicyDocumentService;

public class PolicyDocumentControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PolicyDocumentService service;

	@InjectMocks
	private PolicyDocumentController controller;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testUploadDocument_Success() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "sample content".getBytes());
		when(service.uploadDocument(any(), any()))
				.thenReturn(new PolicyDocumentResponseDto(1L, "LIC", "Sonu", "pdf", null));

		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/documents/upload").file(file).param("title", "LIC")
				.param("author", "Sonu").param("type", "pdf")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
	}

	@Test
	void testUploadDocument_FileMissing() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/documents/upload").param("title", "LIC")
				.param("author", "Sonu").param("type", "pdf")).andExpect(status().isBadRequest());
	}

	@Test
	void testSearch_Success() throws Exception {
		when(service.searchDocuments("LIC"))
				.thenReturn(Collections.singletonList(new QnaSearchResponseDto("LIC Policy", "Snippet")));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/search").param("query", "LIC"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
	}

	@Test
	void testSearch_NotFound() throws Exception {
		when(service.searchDocuments("ABC")).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/search").param("query", "ABC"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value(ResultCode.NOT_FOUND.getCode()));
	}

	@Test
	void testFilter_Success() throws Exception {
		when(service.filterDocuments("Sonu", "pdf", 0, 5))
				.thenReturn(Collections.singletonList(new PolicyDocumentResponseDto(1L, "LIC", "Sonu", "pdf", null)));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/filter").param("author", "Sonu").param("type", "pdf")
				.param("page", "0").param("size", "5")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
	}

	@Test
	void testFilter_NotFound() throws Exception {
		when(service.filterDocuments("Dummy", "txt", 0, 5)).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/filter").param("author", "Dummy")
				.param("type", "txt").param("page", "0").param("size", "5")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(ResultCode.FILTER_RESULT_NOT_FOUND.getCode()));
	}
}