package aws.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client s3Client;
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    //S3에서 장소 데이터 가져오기
    public <T> T getPlaceData(String key, Class<T> valueType){
        GetObjectRequest request = new GetObjectRequest(bucketName, key);

        InputStream inputStream = s3Client.getObject(request).getObjectContent();

        try{
            return objectMapper.readValue(inputStream, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Json 데이터 S3에 저장
    public void savePlaceData(String key, Object value){
        try{
            String jsonData = objectMapper.writeValueAsString(value);

            PutObjectRequest request = new PutObjectRequest(bucketName, key, jsonData);
            s3Client.putObject(request);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

}
