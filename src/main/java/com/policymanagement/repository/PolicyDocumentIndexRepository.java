//package com.policymanagement.repository;
//
//import java.util.List;
//
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Repository;
//
//import com.policymanagement.entity.PolicyDocumentIndex;
//
//@Repository
//public interface PolicyDocumentIndexRepository extends ElasticsearchRepository<PolicyDocumentIndex, Long> {
//    List<PolicyDocumentIndex> findByContentContainingIgnoreCase(String keyword);
//}