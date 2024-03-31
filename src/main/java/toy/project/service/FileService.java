package toy.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        /* 서로 다른 개체들을 구별하기 위해 UUID를 사용 중복성 제거 */
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        /* UUID로 받은 값과 원래 파일의 이름의 확장자를 조합해서 저장될 파일 이름을 만듦 */
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        /* FileOutputStream 클래스는 바이트 단위의 출력을 내보내는 클래스이며,
           생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 생성 */
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        /* fileData를 파일 출력 스트림에 입력 */
        fos.write(fileData);
        fos.close();
        /* 업로드된 파일의 이름을 반환 */
        return savedFileName;
    }


    public void deleteFile(String filePath) throws Exception {
        /* 파일이 저장된 경로를 이용하여 파일 객체를 생성 */
        File deleteFile = new File(filePath);

        /* 해당 파일이 존재하면 파일 삭제 */
        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }



}
