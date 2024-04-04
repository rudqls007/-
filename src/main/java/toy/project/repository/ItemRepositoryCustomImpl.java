package toy.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import toy.project.constant.ItemSellStatus;
import toy.project.dto.ItemSearchDto;
import toy.project.dto.MainItemDto;
import toy.project.dto.QMainItemDto;
import toy.project.entity.Item;
import toy.project.entity.QItem;
import toy.project.entity.QItemImg;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    /* 동적으로 쿼리를 생성하기 위해 JPAQueryFactory 클래스를 사용 */
    private JPAQueryFactory queryFactory;

    /* JPAQueryFactory의 생성자로 EntityManager 개체를 넣어줌. */
    public ItemRepositoryCustomImpl(EntityManager em) {

        this.queryFactory = new JPAQueryFactory(em);
    }

    /* 상품 판매 상태 조건이 전체(null)일 경우는 null을 리턴하고 결과값이 null이면 where절에서 해당 조건은 무시됨.
    *  상품 판매 상태 조건이 null이 아니라 판매중 or 품절 상태라면 해당 조건의 상품만 조회함. */
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        /* searchDateType의 값에 따라서 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된 상품만 조회함.
        *  예를 들어 searchDateType 값이 "1m"인 경우 dateTime의 시간을 한 달 전으로 세팅 후 최근 한달 동안 등록된 상품만 조회하도록 조건값을 반환 */

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);

    }

    /* searchBy의 값에 따라서 상품명에 검색어를 포함하고 있는 상품 또는 상품 생성자의 아이디에 검색어를 포함하고 있는 상품을 조회하도록 조건값 반환 */
    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        /* 이제 queryFactory를 이용해서 쿼리를 생성함. 쿼리문을 직접 장성할 떄의 형태와 문법이 비슷한 걸 볼 수 있음.
        *  - selectForm(QItem.item) : 상품 데이터를 조회하기 위해서 QItem의 item을 지정
        *  - where 조건절 : BooleanExpression 반환하는 조건문을 넣어 줌. ',' 단위로 넣어줄 경우 and 조건으로 인식
        *  - offset : 데이터를 가지고 올 시작 인덱스를 지정
        *  - limit : 한 번에 가조고 올 최대의 개수를 지정
        *  - fetch : 조회한 리스트 및 전체 개수를 포함하는 Query를 반환. */
        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    /* 검색어가 null이 아니면 상품명에 해당 검색어가 포함되는 상품을 조회하는 조건을 반환 */
    private BooleanExpression itemNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        List<MainItemDto> content = queryFactory
                /* QMainItemDto의 생성자에 반환할 값들을 넣어줌.
                *  @QueryProjection을 사용하면 DTO로 바로 조회가 가능함.
                *  엔티티 조회후 DTO로 변화하는 과정을 줄일 수 있음. */
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg)
                /* itemImg와 item을 내부 조인 */
                .join(itemImg.item, item)
                /* 상품 이미지의 경우 대표 상품 이미지만 출력함 */
                .where(itemImg.repimgYn.eq("Y"))
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }
}
