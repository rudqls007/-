package toy.project.service;

import jakarta.persistence.EntityNotFoundException;
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
    @Value("${itemImgLocation}")                // @Value 어노테이션을 통해 application.properties 파일에 등록한 itemImgLocation 값을 불러와서
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";


        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
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

    public void updateItemImg(Long itemImgId,
                              MultipartFile itemImgFile) throws Exception {
        /* 상품 이미지를 수정한 경우 상품 이미지를 업데이트함. */
        if (!itemImgFile.isEmpty()) {
            /* 상품 이미지 아이디를 이용하여 기존에 저장했던 상품 이미지 엔티티를 조회함. */
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            /* 기존에 등록된 상품 이미지 파일이 있을 경우 해당 파일을 삭제 */
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(
                        itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            /* 업데이트한 상품 이미지 파일을 업로드 */
            String imgName = fileService.uploadFile(
                    itemImgLocation, oriImgName, itemImgFile.getBytes());

            String imgUrl = "/images/item/" + imgName;
            /* 변경된 상품 이미지 정보를 세팅함.
            *  여기서 중요한 점은 상품 등록 때처럼 itemImgRepository.save() 로직을 호출하지 않는다는 점.
            *  savedItemImg 엔티티는 현재 영속 상태이므로 데이터를 변경하는 것만으로 변경 감지 기능이 동작하여
            *  트랜잭션이 끝날 때 update 쿼리가 실행됨. 여기서 중요한 것은 엔티티가 영속 상태여야 한다는 것임.*/
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

        }

    }


}
