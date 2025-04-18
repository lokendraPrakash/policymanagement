package com.policymanagement.responseDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDocumentResponseDto {
	private Long id;
	private String title;
	private String author;
	private String type;
	private LocalDate uploadDate;

}
