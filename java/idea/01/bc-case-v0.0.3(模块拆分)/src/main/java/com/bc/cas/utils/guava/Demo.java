package com.bc.cas.utils.guava;

import com.bc.cas.model.entity.Book;
import com.google.common.base.Objects;
import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.*;


/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/12/6.
 * @Version V 1.0.0
 * @Desc
 */
public class Demo {


    /**
     * Optional
     *
     * @return
     */
    public static Optional<Book> getPerson() {
        Book book = new Book();
        Book book2 = new Book();
        book.setName("boa");

        book.setId(10L);
        //类似spring的Assert.state(expr, message)
        checkArgument(book.getId() != 1, " book`s id is %s book`s name is %s", book.getId(), book.getName());

        //空检查
        //book = null;
        checkNotNull(book, "book could not be null");

        //对象比对
        System.out.println("Objects.equal(book, book2)  :: " + Objects.equal(book, book2));

        //通过Objects.toStringHelper构造toString
        System.out.println(Objects.toStringHelper(Book.class).add("id", book.getId()).add("name", book.getName()).toString());

        //返回非空对象的optional
        return Optional.of(book);


    }


    public static void main(String[] args) {

        Optional<Book> optional = getPerson();
        if (optional.isPresent()) {
//            System.out.println(optional.get().getName());
        } else {
//            System.out.println("is null");
        }
    }
}
