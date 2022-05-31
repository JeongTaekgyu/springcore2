package com.sparta.springcore.validator;

import com.sparta.springcore.dto.ProductRequestDto;
import org.springframework.stereotype.Component;

// 스프리엥서 클래스를 쉽게 가져사 사용할 수 있는 방법은
@Component // @Component을 등록하면 bean으로 등록이 된다. 그 bean을 DI받아서 사용하면 된다.
public class ProductValidator {

    // static으로 선언하면 다른곳에서 ProductValidator의 객체를 생성하지 않고 사용 가능하다
    public static void validateProductInput(ProductRequestDto requestDto, Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("회원 Id 가 유효하지 않습니다.");
        }

        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("저장할 수 있는 상품명이 없습니다.");
        }

        if (!URLValidator.isValidUrl(requestDto.getImage())) {
            throw new IllegalArgumentException("상품 이미지 URL 포맷이 맞지 않습니다.");
        }

        if (!URLValidator.isValidUrl(requestDto.getLink())) {
            throw new IllegalArgumentException("상품 최저가 페이지 URL 포맷이 맞지 않습니다.");
        }

        if (requestDto.getLprice() <= 0) {
            throw new IllegalArgumentException("상품 최저가가 0 이하입니다.");
        }
    }

}

