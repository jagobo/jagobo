package com.bc.cas.dao;

import com.bc.cas.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/11/29.
 * @Version V 1.0.0
 * @Desc BookDao接口
 */
@Repository
public interface BookDao extends JpaRepository<Book, Long> {
}
