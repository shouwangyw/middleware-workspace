package com.yw.dao.repository;

import com.yw.dao.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author yangwei
 * @date 2020-10-18 14:24
 */
public interface GoodsRepository extends ElasticsearchCrudRepository<Goods, Long>{

}
