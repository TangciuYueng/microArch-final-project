package cn.edu.tongji.diaryWriting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentimentData {
    private float confidence;  //对文本情感分析的置信度
    private float negativeProb;  //表示属于消极类别的概率，取值范围[0,1]
    private float positiveProb;  //表示属于积极类别的概率 ，取值范围[0,1]
    private float sentiment;  //表示情感极性分类结果，0:负向，1:中性，2:正向
}
