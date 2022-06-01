package com.sparta.springcore.mockobject;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;

import java.util.List;

public class MockProductService {   // 지금은 가짜 객체는 아니고 서비스의 역할을 그대로 복사한 것

    private final MockProductRepository mockProductRepository;
    public static final int MIN_MY_PRICE = 100;

    // Unit 테스트할 때는 스프링 빈이 뜨지 않는다. 그러므로 DI를 해줄 수 있는 방법이 없다.
    // 굳이 db작업이나 ProductRepository를 테스트 하겠다는게 아니라
    // Mock(가짜)ProductRepository을 사용해서 서비스를 검사하겠다는 것이다. ㅇㅇ di를 사용하지 않는 것 보다 중요한 내용이다
    public MockProductService() {
        this.mockProductRepository = new MockProductRepository();
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId ) {
// 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);

        mockProductRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        Product product = mockProductRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        product.setMyprice(myprice);
        mockProductRepository.save(product);

        return product;
    }

    // 회원 ID 로 등록된 상품 조회
    public List<Product> getProducts(Long userId) {
        return mockProductRepository.findAllByUserId(userId);
    }

    // (관리자용) 상품 전체 조회
    public List<Product> getAllProducts() {
        return mockProductRepository.findAll();
    }
}
