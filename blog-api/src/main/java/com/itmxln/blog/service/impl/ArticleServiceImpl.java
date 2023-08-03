package com.itmxln.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmxln.blog.dao.dos.Archives;
import com.itmxln.blog.dao.mapper.ArticleBodyMapper;
import com.itmxln.blog.dao.mapper.ArticleMapper;
import com.itmxln.blog.dao.mapper.ArticleTagMapper;
import com.itmxln.blog.dao.pojo.Article;
import com.itmxln.blog.dao.pojo.ArticleBody;
import com.itmxln.blog.dao.pojo.ArticleTag;
import com.itmxln.blog.dao.pojo.SysUser;
import com.itmxln.blog.service.*;
import com.itmxln.blog.utils.UserThreadLocal;
import com.itmxln.blog.vo.ArticleBodyVo;
import com.itmxln.blog.vo.ArticleVo;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.TagVo;
import com.itmxln.blog.vo.params.ArticleParam;
import com.itmxln.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1.分页查询article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //依据时间以及是否置顶排序
        //order by create_date,weight desc
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //装载返回对象
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id title from artcle order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id title from artcle order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    private List<ArticleVo> copyList(List<Article> recodes,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article recode : recodes) {
            articleVoList.add(copy(recode,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> recodes,boolean isTag,boolean isAuthor){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article recode : recodes) {
            articleVoList.add(copy(recode,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1、根据id查看文章信息
         * 2、根据bodyId和categoryId去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true,true,true);
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //如果需要Tag信息
        if(isTag){
            Long articleID = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleID));
        }
        //如果需要Author信息
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname() );
        }
        //如果需要Body信息
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        //如果需要类别信息
        if(isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId){
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1、发布文章，构建article对象
         * 2、作者id 当前登录用户
         * 3、标签 将标签加入关联列表中（ms_article_tag）
         * 4、body 内容存储
         */
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        //其余属性
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        //先插入一次 生成一个文章id 便于后面更新其他表
        this.articleMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if(tags!=null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        //更新articleBodyId
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }
}
