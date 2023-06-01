package com.project.musinsastocknotificationbot.product.controller;

import com.project.musinsastocknotificationbot.product.domain.dto.Response.ProductFindAllResponse;
import com.project.musinsastocknotificationbot.product.domain.dto.Response.ProductSaveResponse;
import com.project.musinsastocknotificationbot.product.domain.dto.request.ProductSaveRequest;
import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductSaveResponse> save(
        @RequestBody
        @Valid
        ProductSaveRequest productSaveRequest
    ) {
        ProductInfo productInfo = productService.save(productSaveRequest.id(),
            productSaveRequest.size());
        ProductSaveResponse productSaveResponseDto = new ProductSaveResponse(
            productInfo.getId(), productInfo.getSize());
        return ResponseEntity.ok(productSaveResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<ProductFindAllResponse>> findAll() {
        List<ProductFindAllResponse> productFindAllResponses = new ArrayList<>();
        List<Product> products = productService.findAll();

        products.forEach(
            product -> productFindAllResponses.add(ProductFindAllResponse.from(product)));

        return ResponseEntity.ok(productFindAllResponses);
    }

    @DeleteMapping("/{id}/{size}")
    public ResponseEntity<Long> delete(
            @PathVariable("id")
            @NotBlank(message = "삭제 시 id는 필수 입니다.")
            long id,

            @PathVariable("size")
            @NotBlank(message = "삭제 시 size는 필수 입니다.")
            @Pattern(regexp = "[A-Z0-9]+", message = "사이즈 형식에 맞지 않습니다.")
            String size
    ) {
        ProductInfo productInfo = ProductInfo.from(id, size);
        return ResponseEntity.ok(productService.delete(productInfo));
    }
}