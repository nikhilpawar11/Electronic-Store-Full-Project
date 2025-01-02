package com.nikhil.electronic.store.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.nikhil.electronic.store.customresponses.PegiableResponse;

public class Helper {

	
	public static<U, V> PegiableResponse<V> getPeginationResponse(Page<U> page, Class<V> type){
		
		List<U> entity = page.getContent();
		
		List<V> dto = entity.stream().map(ex -> new ModelMapper().map(ex, type)).collect(Collectors.toList());
		
		PegiableResponse<V> pegiableResponse = new PegiableResponse<>();
		
		pegiableResponse.setContent(dto);
		pegiableResponse.setTotalElements(page.getTotalElements());
		pegiableResponse.setTotalPages(page.getTotalPages());
		pegiableResponse.setPageNumber(page.getNumber());
		pegiableResponse.setPageSize(page.getSize());
		pegiableResponse.setIsFirst(page.isFirst());
		pegiableResponse.setIsLast(page.isLast());
		
		return pegiableResponse;
		
	}

}
