package com.policymanagement.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.policymanagement.enumuration.ResultCode;
import com.policymanagement.exceptionHandler.PolicyManagementException;
import com.policymanagement.requestDto.PolicyDocumentRequestDto;
import com.policymanagement.responseDto.ApiResponse;
import com.policymanagement.responseDto.PolicyDocumentResponseDto;
import com.policymanagement.responseDto.QnaSearchResponseDto;
import com.policymanagement.service.PolicyDocumentService;

@RestController
@RequestMapping("/api/documents")
public class PolicyDocumentController {

	@Autowired
	private PolicyDocumentService service;

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> uploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("type") String type) {
		try {
			if (Objects.isNull(file) || file.isEmpty()) {
				throw new PolicyManagementException(ResultCode.FILE_MISSING);
			}
			if (file.getSize() > 10 * 1024 * 1024) {
				throw new PolicyManagementException(ResultCode.FILE_SIZE_EXCEEDED);
			}

			PolicyDocumentRequestDto metadata = new PolicyDocumentRequestDto();
			metadata.setTitle(title);
			metadata.setAuthor(author);
			metadata.setType(type);

			PolicyDocumentResponseDto response = service.uploadDocument(file, metadata);

			return ResponseEntity
					.ok(ApiResponse.builder().code(ResultCode.SUCCESS.getCode()).status(ResultCode.SUCCESS.getStatus())
							.message("Document uploaded successfully").data(response).build());

		} catch (PolicyManagementException e) {
			return ResponseEntity.badRequest().body(ApiResponse.builder().code(e.getResultCode().getCode())
					.status(e.getResultCode().getStatus()).message(e.getResultCode().getMessage()).build());
		} catch (IOException e) {
			throw new PolicyManagementException(ResultCode.FILE_READ_ERROR);
		} catch (Exception ex) {
			throw new PolicyManagementException(ResultCode.FAILED);
		}
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse> search(@RequestParam String query) {
		try {
			if (Objects.isNull(query) || query.trim().isEmpty()) {
				throw new PolicyManagementException(ResultCode.BAD_REQUEST);
			}

			List<QnaSearchResponseDto> result = service.searchDocuments(query.trim());

			if (CollectionUtils.isEmpty(result)) {
				throw new PolicyManagementException(ResultCode.NOT_FOUND);
			}

			return ResponseEntity.ok(ApiResponse.builder().code(ResultCode.SUCCESS.getCode())
					.status(ResultCode.SUCCESS.getStatus()).message("Search successful").data(result).build());

		} catch (PolicyManagementException e) {
			return ResponseEntity.badRequest().body(ApiResponse.builder().code(e.getResultCode().getCode())
					.status(e.getResultCode().getStatus()).message(e.getResultCode().getMessage()).build());
		} catch (Exception ex) {
			throw new PolicyManagementException(ResultCode.FAILED);
		}
	}

	@GetMapping("/filter")
	public ResponseEntity<ApiResponse> filter(@RequestParam String author, @RequestParam String type,
			@RequestParam int page, @RequestParam int size) {
		try {
			List<PolicyDocumentResponseDto> filteredDocs = service.filterDocuments(author, type, page, size);

			if (CollectionUtils.isEmpty(filteredDocs)) {
				throw new PolicyManagementException(ResultCode.FILTER_RESULT_NOT_FOUND);
			}

			return ResponseEntity
					.ok(ApiResponse.builder().code(ResultCode.SUCCESS.getCode()).status(ResultCode.SUCCESS.getStatus())
							.message("Documents filtered successfully").data(filteredDocs).build());

		} catch (PolicyManagementException e) {
			return ResponseEntity.badRequest().body(ApiResponse.builder().code(e.getResultCode().getCode())
					.status(e.getResultCode().getStatus()).message(e.getResultCode().getMessage()).build());
		} catch (Exception ex) {
			throw new PolicyManagementException(ResultCode.FAILED);
		}
	}

}
