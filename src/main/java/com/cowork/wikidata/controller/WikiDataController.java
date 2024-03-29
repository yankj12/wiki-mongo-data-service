package com.cowork.wikidata.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cowork.common.util.SchameCopyUtil;
import com.cowork.wikidata.dao.ArticleMongoDaoUtil;
import com.cowork.wikidata.model.Article;
import com.cowork.wikidata.vo.ArticleVo;
import com.cowork.wikidata.vo.ResponseVo;

@RestController
public class WikiDataController {
	
	@Autowired
	private ArticleMongoDaoUtil articleMongoDaoUtil;

	/**
	 * 根据userId查询这个用户的数据权限
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/api/saveArticle", method=RequestMethod.POST)
    public ResponseVo saveArticle(@RequestBody ArticleVo articalVo) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorMsg(null);
		
		if(articalVo != null) {
			Article article = (Article)SchameCopyUtil.simpleCopy(articalVo, Article.class);
			if(articalVo.getId() != null && !"".equals(articalVo.getId().trim())) {
				// 避免将insertTime字段进行更新
				article.setInsertTime(null);
				article.setUpdateTime(new Date());
				articleMongoDaoUtil.updateArticle(article);
			}else {
				article.setInsertTime(new Date());
				article.setUpdateTime(new Date());
				String id = articleMongoDaoUtil.insertArticle(article);
				articalVo.setId(id);
			}
			responseVo.setSuccess(true);
		}
		responseVo.setArticle(articalVo);
        return responseVo;
    }
	
	@RequestMapping(value="/api/queryArticle", method=RequestMethod.GET)
    public ResponseVo queryArticle(@RequestParam String id) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorMsg(null);
		
		if(id != null) {
			Article article = articleMongoDaoUtil.findArticleById(id);
			if(article != null){
				ArticleVo articalVo = (ArticleVo)SchameCopyUtil.simpleCopy(article, ArticleVo.class);
				responseVo.setArticle(articalVo);
				
				responseVo.setSuccess(true);
			}else{
				responseVo.setErrorMsg("article not found.");;
				responseVo.setSuccess(false);
			}
		}
		
        return responseVo;
    }
	
	@RequestMapping(value="/api/deleteArticle", method=RequestMethod.GET)
    public ResponseVo deleteArticle(@RequestParam String id) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorMsg(null);
		
		if(id != null && !"".equals(id.trim())) {
			Map<String, Object> map = new HashMap<>();
			map.put("validStatus", "0");
			map.put("updateTime", new Date());
			
			articleMongoDaoUtil.updateArticleMultiFieldsById(id, map);
			responseVo.setSuccess(true);
		}
		
        return responseVo;
    }
	
	@RequestMapping(value="/api/searchArticle", method=RequestMethod.GET)
    public ResponseVo searchArticle(@RequestParam String key, @RequestParam int pageNo, @RequestParam int pageSize) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorMsg(null);
		
		if(key != null && !"".equals(key.trim())) {
			Map<String, Object> condition = new HashMap<>();
			condition.put("title", key);
			condition.put("content", key);
			condition.put("author", key);
			
			if (pageNo <= 0) {
				pageNo = 1;
			}
			if (pageSize <= 0) {
				pageSize = 10;
			}
			
			condition.put("page", pageNo);
			condition.put("rows", pageSize);
			
			Long total = articleMongoDaoUtil.countArticleDocumentsByCondition(condition);
			List<Article> articles = articleMongoDaoUtil.findArticleDocumentsByCondition(condition);
			
			if(articles != null){
				List<ArticleVo> articleVos = new ArrayList<>();
				for(Article article:articles) {
					ArticleVo articalVo = (ArticleVo)SchameCopyUtil.simpleCopy(article, ArticleVo.class);
					articleVos.add(articalVo);
				}
				responseVo.setArticles(articleVos);
				responseVo.setTotal(total.intValue());
				
				responseVo.setSuccess(true);
			}else{
				responseVo.setErrorMsg("article not found.");;
				responseVo.setSuccess(false);
			}
		}
		
        return responseVo;
    }
}