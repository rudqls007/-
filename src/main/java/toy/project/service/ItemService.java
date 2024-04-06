package toy.project.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toy.project.dto.ItemFormDto;
import toy.project.dto.ItemImgDto;
import toy.project.dto.ItemSearchDto;
import toy.project.dto.MainItemDto;
import toy.project.entity.Item;
import toy.project.entity.ItemImg;
import toy.project.repository.ItemImgRepository;
import toy.project.repository.ItemRepository;

import java.util.ArrayList;
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
            }

            /* 상품 이미지 정보를 저장 */
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();

    }

    /* 상품 데이터를 읽어오는 트랜잭션을 읽기 전용으로 설정함.
       이럴 경우 JPA가 더티체킹(변경 감지)을 수행하지 않아서 성능을 향상시킬 수 있음. */
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        /* 해당 상품의 이미지를 조회함. 등록순으로 가지고 오기 위해서 상품 이미지 아이디마다 오름차순으로 가지고 오는 로직 */
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        /* 조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가함. */
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);

        }

        /* 상품의 아이디를 통해 상품 엔티티를 조회함. 존재하지 않을 때는 EntityNotFoundException을 발생 */
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }


    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        /* 상품 등록 화면으로부터 전달 받은 상품 아이디를 이용하여 상품 엔티티를 조회함. */
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        /* 상품 등록 화면으로부터 전달 받은 ItemFormDto를 통해 상품 엔티티를 업데이트 */
        item.updateItem(itemFormDto);

        /* 상품 이미지 아이디 리스트를 조회 */
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        /* 상품 이미지를 업데이트 하기 위해서 updateItemImg() 메소드에 상품 이미지 아이디와 상품 이미지 파일 정보를 파라미터로 전달. */
        for(int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i) );
        }

        return item.getId();
    }

    /* 상품 조회 조건과 페이지 정보를 파라미터로 받아서 상품 데이터를 조회하는 메소드
    *  데이터의 수정이 일어나지 않으므로 최적화를 위해 readOnly = true 지정 */
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    /* 메인 페이지에 보여줄 상품 데이터를 조회하는 메소드 */
    @Transactional
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

}