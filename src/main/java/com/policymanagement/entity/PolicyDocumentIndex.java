//package com.policymanagement.entity;
//
//import java.time.LocalDate;
//
//import org.springframework.data.elasticsearch.annotations.Document;
//
//import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Document(indexName = "policy_documents")
//public class PolicyDocumentIndex {
//
//    @Id
//    private Long id;
//    private String title;
//    private String author;
//    private String type;
//    private LocalDate uploadDate;
//    private String content;
//}