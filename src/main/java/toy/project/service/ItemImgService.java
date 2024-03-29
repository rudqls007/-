package toy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import toy.project.entity.ItemImg;
import toy.project.repository.ItemImgRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {


    /* value 어노테이션을 통해 applicaiotn.properties 파일에 등록한 itemimgLocation 값을 불러와서 itemImgLocation 변수에 넣어줌 */
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public  void  saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";


        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            /* 사용자가 상품의 이미지를 등록했다면 저장할 경로와 파일의 이름, 파일을 파일의 바이트 배열을 파일 업로드 파라미터로
            *  uploadFIle 메소드 호출함. 호출 결과 로컬에 저장된 파일의 이름을 imgName 변수에 저장*/
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            /* 저장한 상품 이미지를 불러올 경로를 설정 후 외부 리소스를 불러오는 urlPatterns으로 WebMvxConfig 클래스에서
            *  "/images/**"를 설정함. 또한 application.properties에서 설정한 uploadPath 프로퍼티 경로인
            *  "C:/shop/" 아래 item 폴더에 이미지를 저장하므로 상품 이미지를 불러오는 경로로 "/images/item/"를 붙여줌.  */
            imgUrl = "/images/item/" + imgName;
        }


        /* 입력 받은 상품 이미지 정보를 저장함
        *  imgName : 실제 로컬에 저장된 상품 이미지 파일의 이름
        *  oriImgName : 업로드 했던 상품 이미지 파일의 원래 이름
        *  imgUrl : 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로 */
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }
}
