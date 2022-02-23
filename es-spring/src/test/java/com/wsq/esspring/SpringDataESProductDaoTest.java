package com.wsq.esspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESProductDaoTest {

    @Autowired
    private ProductDao productDao;


    /**
     * 添加
     */
    @Test
    public void save(){
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("华为");
        product.setPrice(2999.00);
        product.setImages("http://www.atgui/hw.jpg");
        productDao.save(product);
    }

    /**
     * 修改
     */
    @Test
    public void update(){
        Product product = new Product();
        //id相同修改，不同就新增
        product.setId(2L);
        product.setTitle("小米2手机");
        product.setCategory("手机");
        product.setPrice(9999.00);
        product.setImages("http://www.atgui/xm.jpg");
        productDao.save(product);
    }

    /**
     * 根据id查询
     */
    @Test
    public void findById(){
        Product product = productDao.findById(1L).get();
        System.out.println(product);
    }

    /**
     * 查询所有
     */
    @Test
    public void findAll(){
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    /**
     * 删除
     */
    @Test
    public void delete(){
        Product product = new Product();
        product.setId(1L);
        productDao.delete(product);
    }

    /**
     * 批量新增
     */
    @Test
    public void saveAll(){
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("["+i+"]小米手机");
            product.setCategory("手机");
            product.setPrice(1999.00+i);
            product.setImages("http://www.wsq/xm.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }

    /**
     * 分页查询
     */
    @Test
    public void findByPageable(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int from=0;//当前页，第一页从0开始
        int size=5;//每页显示多少跳

        //设置分页查询
        PageRequest pageRequest = PageRequest.of(from, size, sort);
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product product : productPage.getContent()) {
            System.out.println(product);
        }
    }

}
