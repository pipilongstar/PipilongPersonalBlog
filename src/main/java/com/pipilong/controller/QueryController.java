package com.pipilong.controller;

import com.pipilong.pojo.Discuss;
import com.pipilong.service.QueryService;
import com.pipilong.service.SubmitElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/6
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private SubmitElasticSearchService submitElasticSearchService;

    @Autowired
    private QueryService queryService;

    /**
     * 查询讨论卡片信息
     * @param conditional 条件
     * @return 查询的信息
     * @throws IOException io异常
     */
    @PostMapping("/es")
    public ResponseEntity<String> esQuery(@RequestBody String conditional) throws Exception {

        String data = submitElasticSearchService.query(conditional);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * 向es发起搜索
     * @param conditional 搜索条件
     * @return 搜索得到的信息
     * @throws IOException io异常
     */
    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestBody String conditional) throws Exception {

        String data = submitElasticSearchService.search(conditional);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/rankingList")
    public ResponseEntity<List<Discuss>> selectRankingList(){

        List<Discuss> rankingList = queryService.selectRankingList();

        return new ResponseEntity<>(rankingList,HttpStatus.OK);
    }

}


































