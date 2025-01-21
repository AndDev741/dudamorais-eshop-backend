package com.dudamorais.eshop.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.dudamorais.eshop.domain.ProductService;
import com.dudamorais.eshop.domain.dto.CreateProductDTO;




@RestController
@RequestMapping("/product")
public class ProductController {
    private final AmazonS3 amazonS3;

    private final ProductService productService;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public ProductController(AmazonS3 amazonS3, ProductService productService){
        this.amazonS3 = amazonS3;
        this.productService = productService;
    }   
    

    @PostMapping("/generateS3Url")
    public ResponseEntity<Map<String, String>> generateS3Url(@RequestBody Map<String, String> body){
        try{
            String fileName = body.get("fileName");
            String encodedFileName = UriUtils.encodePath(fileName, StandardCharsets.UTF_8);
            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

            String publicUrl = String.format("https://%s.s3.us-east-1.amazonaws.com/%s",
                                                    bucketName,
                                                    encodedFileName);

            Map<String, String> response = new HashMap<>();
            response.put("publicUrl", publicUrl);
            response.put("presignedUrl", url.toString());

            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody CreateProductDTO createProductDTO){
        return productService.createProduct(createProductDTO);
    }
}
