package toy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toy.project.dto.ItemFormDto;
import toy.project.entity.Item;
import toy.project.entity.ItemImg;
import toy.project.repository.ItemImgRepository;
import toy.project.repository.ItemRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        /* 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성 */
        Item item = itemFormDto.createItem();
        /* 상품 데이터 저장 */
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            /* 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 Y로 세팅하고 나머지 상품 이미지는 N으로 설정함. */
            if (i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
                /* 상품 이미지 정보를 저장 */
                itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
            }

        }

        return item.getId();

    }

    }