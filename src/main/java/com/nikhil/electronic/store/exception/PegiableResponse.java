package com.nikhil.electronic.store.exception;

import java.util.List;

import com.nikhil.electronic.store.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PegiableResponse {
	
	private List<UserDto> content;
	
	private long totalElements;
	
	private int totalPages;
	
	private int pageNumber;
	
	private int pageSize;
	
	private Boolean isFirst;
	
	private Boolean isLast;

}
