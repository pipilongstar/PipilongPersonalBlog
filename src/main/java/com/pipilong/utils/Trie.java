package com.pipilong.utils;

import jdk.internal.net.http.common.Pair;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
/**
 * @author pipilong
 * @createTime 2023/7/17
 * @description
 */
@Component
public class Trie {

    private final TrieNode root;

    private final StringBuilder stringBuilder;

    private final IKSegmenter ik;

    public Trie(){
        ik = new IKSegmenter(null,true);
        root = new TrieNode();
        stringBuilder = new StringBuilder();
    }

    //插入敏感词
    public void insert(String word){
        TrieNode cur = root;
        for(char ch : word.toCharArray()){
            cur.children.putIfAbsent(ch,new TrieNode());
            cur = cur.children.get(ch);
        }
        cur.isEnd=true;
    }

    //在前缀树中查找是否有该词
    private boolean search(String word){
        TrieNode cur = root;
        for(char ch : word.toCharArray()){
            cur = cur.children.get(ch);
            if(cur==null) return false;
        }
        return cur.isEnd;
    }

    /**
     * 获取句子中的敏感词
     * @param sentence 用户发送的句子
     * @return  过滤后的句子
     * @throws IOException io异常
     */
    public String filterSensitiveWords(String sentence) throws IOException {
        Map<String, Pair<Integer, Integer>> map = this.analyzerSensitive(sentence);
        map.forEach((k,v)->{
            if(search(k)) {
                stringBuilder.replace(v.first,v.second,"***");
            }
        });
        return stringBuilder.toString();
    }

    /**
     * 分析句子，并把数据存起来
     * @param sentence 句子
     * @return 返回解析后的数据
     * @throws IOException io异常
     */
    private Map<String, Pair<Integer,Integer>> analyzerSensitive(String sentence) throws IOException {
        stringBuilder.append(sentence);
        ik.reset(new StringReader(sentence));
        Lexeme next;
        Map<String, Pair<Integer,Integer>> map = new HashMap<>();
        while( (next = ik.next()) != null){
            map.put(next.getLexemeText(),new Pair<>(next.getBeginPosition(),next.getEndPosition()));
        }
        return map;
    }

    private static class TrieNode{
        Map<Character,TrieNode> children;
        boolean isEnd;

        public TrieNode() {
            children = new HashMap<>();
            isEnd=false;
        }
    }

}
