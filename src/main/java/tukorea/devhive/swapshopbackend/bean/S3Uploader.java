package tukorea.devhive.swapshopbackend.bean;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
@Service
@Slf4j
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile,String dirName) throws IOException{
        File uploadFile=convert(multipartFile)
                .orElseThrow(()->new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return uploadT(uploadFile,dirName);
    }

    private String uploadT(File uploadFile, String dirName) {
        String fileName=dirName+"/"+ UUID.randomUUID() +uploadFile.getName();
        String uploadImageUrl=putS3(uploadFile,fileName);
        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {

        // mac 환경에서 파일이름에 공백이 허용이 되기때문에 공백을 다른문자로 대체해야함
        String newFilename = file.getOriginalFilename().replaceAll("\\s+", "_"); // 공백을 언더스코어로 대체
        System.out.println(newFilename);
        File convertFile = new File(newFilename);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    // S3에서 이미지 삭제
    public void deleteImage(String imageUrl) {
        try {
            // 이미지 URL에서 fileName을 추출합니다.
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            if (amazonS3Client.doesObjectExist(bucket, "images/"+fileName)) {
                amazonS3Client.deleteObject(bucket, "images/"+fileName);
                log.info("S3에서 이미지가 삭제되었습니다.");
            } else {
                log.info("해당 이미지가 S3 버킷에 존재하지 않습니다.");
            }
        } catch (Exception e) {
            log.error("S3 이미지 삭제 중 오류 발생: " + e.getMessage());
        }
    }




}
