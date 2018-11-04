/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.es.ProductESDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.SpringContextUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames="products")
public class ProductService  {
	
	@Autowired ProductDAO productDAO;
	@Autowired
	ProductESDAO productESDAO;//更新添加删除 和ProductDAO一起
	@Autowired ProductImageService productImageService;
	@Autowired CategoryService categoryService;
	@Autowired OrderItemService orderItemService;
	@Autowired ReviewService reviewService;

	@CacheEvict(allEntries=true)
	public void add(Product bean) {
		productDAO.save(bean);
		productESDAO.save(bean);
	}

	@CacheEvict(allEntries=true)
	public void delete(int id) {
		productDAO.delete(id);
		productESDAO.delete(id);
	}

	@Cacheable(key="'products-one-'+ #p0")
	public Product get(int id) {
		return productDAO.findOne(id);
	}

	@CacheEvict(allEntries=true)
	public void update(Product bean) {
		productDAO.save(bean);
		productESDAO.save(bean);
	}

	@Cacheable(key="'products-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
	public Page4Navigator<Product> list(int cid, int start, int size,int navigatePages) {
    	Category category = categoryService.get(cid);
    	Sort sort = new Sort(Sort.Direction.DESC, "id");
    	Pageable pageable = new PageRequest(start, size, sort);    	
    	Page<Product> pageFromJPA =productDAO.findByCategory(category,pageable);
    	return new Page4Navigator<>(pageFromJPA,navigatePages);
	}

	public void fill(List<Category> categorys) {
		for (Category category : categorys) {
			fill(category);
		}
	}

	
	@Cacheable(key="'products-cid-'+ #p0.id")
	public List<Product> listByCategory(Category category){
		return productDAO.findByCategoryOrderById(category);
	}

	//这个 listByCategory 方法本来就是 ProductService 的方法，却不能直接调用。 为什么呢？ 因为 springboot 的缓存机制是通过切面编程 aop来实现的。 从fill方法里直接调用 listByCategory 方法， aop 是拦截不到的，也就不会走缓存了。 所以要通过这种 绕一绕 的方式故意诱发 aop, 这样才会想我们期望的那样走redis缓存。
	public void fill(Category category) {

		ProductService productService = SpringContextUtil.getBean(ProductService.class);
		List<Product> products = productService.listByCategory(category);
		productImageService.setFirstProdutImages(products);
		category.setProducts(products);

	}

	
	public void fillByRow(List<Category> categorys) {
        int productNumberEachRow = 8;
        for (Category category : categorys) {
            List<Product> products =  category.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }		
	}

	
	public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product);
        product.setSaleCount(saleCount);

        
        int reviewCount = reviewService.getCount(product);
        product.setReviewCount(reviewCount);
        
	}

	
	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product product : products) {
			setSaleAndReviewNumber(product);
		}
	}

	//mark  es 查询 搜索keyword
	public List<Product> search(String keyword, int start, int size) {

		initDatabase2ES();

		//es 类
		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
				.add(QueryBuilders.matchPhraseQuery("name", keyword),
						ScoreFunctionBuilders.weightFactorFunction(100))
				.scoreMode("sum")
				.setMinScore(10);


		//spring data 类
		Sort sort  = new Sort(Sort.Direction.DESC,"id");//按id倒叙 返回结果
		Pageable pageable = new PageRequest(start, size,sort);

		//spring es
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withPageable(pageable)
				.withQuery(functionScoreQueryBuilder).build();

		Page<Product> page = productESDAO.search(searchQuery);
		return page.getContent();
	}

	//mark 初始化数据到es.
	// 因为数据刚开始都在数据库中，不在es中，
	// 所以刚开始查询，先看看es有没有数据，如果没有，就把数据从数据库同步到es中。
	private void initDatabase2ES() {

		//尝试拿5条看看有没有
		Pageable pageable = new PageRequest(0, 5);

		Page<Product> page =productESDAO.findAll(pageable);

		//没有就全部都要去数据库取出来 放到es
		if(page.getContent().isEmpty()) {

			List<Product> products= productDAO.findAll();
			for (Product product : products) {
				productESDAO.save(product);
			}
		}
	}

}


