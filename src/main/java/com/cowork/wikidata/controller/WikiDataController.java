package com.cowork.wikidata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
				articleMongoDaoUtil.updateArticle(article);
			}else {
				String id = articleMongoDaoUtil.insertArticle(article);
				articalVo.setId(id);
			}
			responseVo.setSuccess(true);
		}
		responseVo.setArticle(articalVo);
        return responseVo;
    }
	
}