package com.policymanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.policymanagement.entity.PolicyDocument;

public interface PolicyDocumentRepository extends JpaRepository<PolicyDocument, Long> {
	@Query(value = "SELECT * FROM policy_documents WHERE LOWER(CAST(content AS CHAR)) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<PolicyDocument> searchByKeyword(@Param("keyword") String keyword);

    List<PolicyDocument> findByAuthorContainingIgnoreCaseAndTypeContainingIgnoreCase(String author, String type,
            Pageable pageable);
}