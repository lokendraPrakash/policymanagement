//package com.policymanagement.controller;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.policymanagement.entity.PolicyDocumentIndex;
//import com.policymanagement.responseDto.ApiResponse;
//import com.policymanagement.service.PolicyDocumentElasticSearchService;
//
//@RestController
//@RequestMapping("/api/elasticsearch")
//public class PolicyDocumentElasticSearchController {
//
//	 private final PolicyDocumentElasticSearchService searchService;
//
//	    public PolicyDocumentElasticSearchController(PolicyDocumentElasticSearchService searchService) {
//	        this.searchService = searchService;
//	    }
//
//
//	@PostMapping("/index")
//	public ResponseEntity<ApiResponse> indexDocument(@RequestBody PolicyDocumentIndex document) {
//		searchService.saveDocument(document);
//		return ResponseEntity
//				.ok(ApiResponse.builder().code(200).status("SUCCESS").message("Document indexed successfully").build());
//	}
//
//	@GetMapping("/search")
//	public ResponseEntity<ApiResponse> searchDocuments(@RequestParam String keyword) {
//		List<PolicyDocumentIndex> result = searchService.searchByKeyword(keyword);
//		return ResponseEntity
//				.ok(ApiResponse.builder().code(200).status("SUCCESS").message("Search complete").data(result).build());
//	}
//}