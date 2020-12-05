package com.yw.domain;

import com.yw.dao.entity.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author yangwei
 * @date 2020-10-18 14:17
 */
@Data
@Accessors(chain = true)
public class SearchResult {
    private List<Goods> goodsList;
    private List<?> aggs;
    private Integer page;
    private Integer totalPage;
}
