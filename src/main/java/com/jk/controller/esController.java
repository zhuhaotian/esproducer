package com.jk.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jk.bean.Mallsku;
import com.jk.utils.RestClientFactory;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("es")
public class esController {

 //组合查询
 @ResponseBody
 @RequestMapping("esselect")
 public List  essku(Mallsku mallsku){
  ArrayList list = new ArrayList();
  SearchRequest SearchRequest  = new SearchRequest("es"); //{}
  SearchSourceBuilder searchsourcebuilder = new SearchSourceBuilder();  //query

  BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();  //boll
  if(!StringUtils.isEmpty(mallsku.getChjshj())){
   RangeQueryBuilder chjshj = QueryBuilders.rangeQuery("chjshj");
   BoolQueryBuilder must3 = boolQueryBuilder.must(chjshj);
   searchsourcebuilder.query(must3);
   if(!StringUtils.isEmpty(mallsku.getStatusdate())){
    chjshj.gte(mallsku.getStatusdate());
   }
   if(!StringUtils.isEmpty(mallsku.getEnddate())){
    chjshj.lte(mallsku.getEnddate());
   }
  }
  if(!StringUtils.isEmpty(mallsku.getSku_mch())){
   MatchQueryBuilder sku_mch = QueryBuilders.matchQuery("sku_mch", mallsku.getSku_mch());
   BoolQueryBuilder must = boolQueryBuilder.must(sku_mch);
   searchsourcebuilder.query(must);
  }
  if(!StringUtils.isEmpty(mallsku.getSku_xl())){
   TermQueryBuilder sku_xl = QueryBuilders.termQuery("sku_xl", mallsku.getSku_xl());
   BoolQueryBuilder must1 = boolQueryBuilder.must(sku_xl);
   searchsourcebuilder.query(must1);
  }
  if(!StringUtils.isEmpty(mallsku.getJg())){
   TermQueryBuilder jg = QueryBuilders.termQuery("jg",mallsku.getJg());
   BoolQueryBuilder must2 = boolQueryBuilder.must(jg);
   searchsourcebuilder.query(must2);
  }
  SearchRequest.source(searchsourcebuilder);
     try {
         SearchResponse search = RestClientFactory.getHighLevelClient().search(SearchRequest);
         JSONObject parseObject = JSONObject.parseObject(String.valueOf(search));
         JSONObject hits = parseObject.getJSONObject("hits");
         JSONArray hits1 = hits.getJSONArray("hits");
         for (Object o : hits1) {
             JSONObject parseObject2 = JSONObject.parseObject(String.valueOf(o));
             JSONObject source = parseObject2.getJSONObject("_source");
             list.add(source);
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
     System.out.println(list);
     return list;
  }

}
