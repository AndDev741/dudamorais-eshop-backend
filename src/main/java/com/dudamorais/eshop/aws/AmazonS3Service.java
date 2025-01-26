package com.dudamorais.eshop.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class AmazonS3Service {
    
    @Autowired
    private AmazonS3 amazonS3;

    private final String bucketName = "dudamoraisbucket";

    public void deleteFile(String fileUrl) {
        String fileKey = extractKeyFromUrl(fileUrl);

        if(fileKey != null){
            amazonS3.deleteObject(bucketName, fileKey);
            System.out.println("Arquivo excluido com sucesso: " + fileKey);
        }else{
            throw new IllegalArgumentException("URL inválida ou chave não encontrada");
        }
    }

    public String extractKeyFromUrl(String fileUrl){
        try{
            String[] urlParts = fileUrl.split("/");
            return urlParts[urlParts.length - 1];

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
