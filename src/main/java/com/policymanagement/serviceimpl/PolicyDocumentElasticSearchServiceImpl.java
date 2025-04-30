//package com.policymanagement.serviceimpl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.policymanagement.entity.PolicyDocumentIndex;
//import com.policymanagement.repository.PolicyDocumentIndexRepository;
//import com.policymanagement.service.PolicyDocumentElasticSearchService;
//
//@Service
//public class PolicyDocumentElasticSearchServiceImpl implements PolicyDocumentElasticSearchService {
//	
//	@Autowired
//	private PolicyDocumentIndexRepository repository;
//
//	@Override
//	public void saveDocument(PolicyDocumentIndex documentIndex) {
//		repository.save(documentIndex);
//	}
//
//	@Override
//	public List<PolicyDocumentIndex> searchByKeyword(String keyword) {
//		return repository.findByContentContainingIgnoreCase(keyword);
//	}
//}