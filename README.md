# wiki-mongo-data-service
wiki-mongo-data-service 是为 [GitHub yankj12/wiki](https://github.com/yankj12/wiki) 项目提供数据服务的，计划全部实现其所需的服务。

yankj12/wiki 需要哪些 API ，请参考 [yankj12/wiki](https://github.com/yankj12/wiki)

***
# 计划提供的API
| API URL | 描述 | 交互数据示例 |
|---|---|---|
| /api/saveArticle | 保存文章 | [示例](#example-api-saveArticle) |
| /api/queryArticle?id= | 查询文章 | [示例](#example-api-queryArticle) |
| /api/searchArticle?key= | 搜索文章 | [示例](#example-api-searchArticle) |

初版api没有采用 RESTful 风格，后续会进行改进

***
# example-api-saveArticle
```
request
{
	title : '',
	content : '',
	author : 'jim',
	insertTime : '2018-04-18 20:00:00',
	updateTime : '2018-04-18 20:00:00'
}

response
{
	success : true,
	errorMsg : '',
	id : '',
	title : '',
	content : '',
	author : 'jim',
	insertTime : '2018-04-18 20:00:00',
	updateTime : '2018-04-18 20:00:00'
}
```

# example-api-queryArticle
```
request
/api/queryArticle?id=

response
{
	success : true,
	errorMsg : '',
	id : '',
	title : '',
	content : '',
	author : 'jim',
	insertTime : '2018-04-18 20:00:00',
	updateTime : '2018-04-18 20:00:00'
}
```

# example-api-searchArticle
```
request
/api/searchArticle?key=

response
{
	success : true,
	errorMsg : '',
	total : 150,
	pageNo : 1,
	pageSize : 20,
	ids : []
}
```
