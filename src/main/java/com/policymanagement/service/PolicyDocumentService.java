package com.policymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.policymanagement.requestDto.PolicyDocumentRequestDto;
import com.policymanagement.responseDto.PolicyDocumentResponseDto;
import com.policymanagement.responseDto.QnaSearchResponseDto;

@Service
public interface PolicyDocumentService {

	PolicyDocumentResponseDto uploadDocument(MultipartFile file, PolicyDocumentRequestDto metadata) throws Exception;

	List<QnaSearchResponseDto> searchDocuments(String query);

	List<PolicyDocumentResponseDto> filterDocuments(String author, String type, int page, int size);

}
