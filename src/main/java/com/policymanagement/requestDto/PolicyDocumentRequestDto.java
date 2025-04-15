package com.policymanagement.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDocumentRequestDto {
	
	private String title;
    private String author;
    private String type;

}
