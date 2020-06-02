package life.majiang.community.community.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPagination(Integer totalCount, Integer page, Integer size) {





        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }



        if(page <=1 )
        {
            page =1 ;
        }
        if (page >= totalPage)
        {
            page = totalPage;
        }
        this.page = page;

//        pages.add(page);

//        for (int i = 1; i <= 3; i++) {
//            if(page -i > 0){
//                pages.add(0,page - i);
//            }
//
//            if (page +i <= totalPage){
//                pages.add(page + i);
//            }
//
//        }



       if(page -1  > 0 && page +1 <totalPage) {
            pages.add(0, page - 1);
            pages.add(1, page);
            pages.add(2, page + 1);
        }
       if(page -1 <= 0)
        {
            pages.add(0, 1);
            pages.add(1, 2);
            pages.add(2, 3);
        }
       if(page + 2 > totalPage)
        {
            pages.add(0, totalPage-2);
            pages.add(1, totalPage-1 );
            pages.add(2, totalPage );
        }

        if (totalPage < 3)
        {
            pages.clear();
            for (int i = 0; i < totalPage ; i++) {
                pages.add(0,i+1);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        if (pages.contains(1)) {
            showFirstPage = true;
        } else {
            showFirstPage = true;
        }

        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }

}





