package com.policymanagement.serviceImpl;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.policymanagement.entity.PolicyDocument;
import com.policymanagement.enumuration.ResultCode;
import com.policymanagement.exceptionHandler.PolicyManagementException;
import com.policymanagement.repository.PolicyDocumentRepository;
import com.policymanagement.requestDto.PolicyDocumentRequestDto;
import com.policymanagement.responseDto.PolicyDocumentResponseDto;
import com.policymanagement.responseDto.QnaSearchResponseDto;
import com.policymanagement.service.PolicyDocumentService;

@Service
public class PolicyDocumentServiceImpl implements PolicyDocumentService {

	@Autowired
	private PolicyDocumentRepository repository;

	@Override
	public PolicyDocumentResponseDto uploadDocument(MultipartFile file, PolicyDocumentRequestDto metadata)
			throws Exception {

		if (file == null || file.isEmpty()) {
			throw new PolicyManagementException(ResultCode.FILE_MISSING);
		}

		if (file.getSize() > 10 * 1024 * 1024) {
			throw new PolicyManagementException(ResultCode.FILE_SIZE_EXCEEDED);
		}

		String content = extractTextFromFile(file); 

		PolicyDocument document = PolicyDocument.builder().title(metadata.getTitle()).author(metadata.getAuthor())
				.type(metadata.getType()).uploadDate(LocalDate.now()).content(content).build();

		document = repository.save(document);

		return PolicyDocumentResponseDto.builder().id(document.getId()).title(document.getTitle())
				.author(document.getAuthor()).type(document.getType()).uploadDate(document.getUploadDate()).build();
	}

	private String extractTextFromFile(MultipartFile file) {
		try (InputStream input = file.getInputStream()) {
			BodyContentHandler handler = new BodyContentHandler(-1); 
			Metadata metadata = new Metadata();
			AutoDetectParser parser = new AutoDetectParser();
			ParseContext context = new ParseContext();

			parser.parse(input, handler, metadata, context);
			return handler.toString();
		} catch (Exception e) {
			throw new PolicyManagementException(ResultCode.FILE_READ_ERROR);
		}
	}

	@Override
	public List<QnaSearchResponseDto> searchDocuments(String query) {
		List<PolicyDocument> docs = repository.searchByKeyword(query);

		return docs.stream().map(doc -> QnaSearchResponseDto.builder().title(doc.getTitle())
				.snippet(getSnippet(doc.getContent(), query)).build()).collect(Collectors.toList());
	}

	private String getSnippet(String content, String keyword) {
		int index = content.toLowerCase().indexOf(keyword.toLowerCase());
		if (index == -1)
			return "No preview found.";
		int start = Math.max(0, index - 30);
		int end = Math.min(content.length(), index + 70);
		return content.substring(start, end) + "...";
	}

	@Override
	public List<PolicyDocumentResponseDto> filterDocuments(String author, String type, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return repository.findByAuthorContainingIgnoreCaseAndTypeContainingIgnoreCase(author, type, pageable).stream()
				.map(this::mapToPolicyDto).collect(Collectors.toList());
	}

	private PolicyDocumentResponseDto mapToPolicyDto(PolicyDocument doc) {
		return PolicyDocumentResponseDto.builder().id(doc.getId()).title(doc.getTitle()).author(doc.getAuthor())
				.type(doc.getType()).uploadDate(doc.getUploadDate()).build();
	}

}
