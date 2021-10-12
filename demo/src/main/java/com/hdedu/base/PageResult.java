package com.hdedu.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    @ApiModelProperty("页码")
    private Integer pageNum;
    @ApiModelProperty("分页大小")
    private Integer pageSize;
    @ApiModelProperty("数据总条数")
    private Long total;
    @ApiModelProperty("总页数")
    private Integer pages;
    private List<T> list;


    public static <T> PageResult<T> getPage(List<T> list){
        PageResult pageResult = new PageResult();
        pageResult.setList(list);
        return pageResult;
    }
    public static <T> PageResult<T> getPage(Integer pageNum,Integer pageSize,Long total,Integer pages,List<T> list){
        PageResult pageResult = new PageResult();
        pageResult.setList(list);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setPages(pages);
        pageResult.setTotal(total);
        return pageResult;
    }
}
