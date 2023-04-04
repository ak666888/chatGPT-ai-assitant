package cn.zhang.ai.api.domain.ai.service;

import cn.zhang.ai.api.domain.ai.IOpenAI;

import cn.zhang.ai.api.domain.ai.model.aggregates.AIAnswer;
import cn.zhang.ai.api.domain.ai.model.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OpenAI implements IOpenAI {
    private Logger logger = LoggerFactory.getLogger(OpenAI.class);
    @Override
    public String doChatGpt(String openaiKey, String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

//         代理地址；open.aiproxy.xyz、open2.aiproxy.xyz
        HttpPost post = new HttpPost("https://open2.aiproxy.xyz/v1/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer "+ openaiKey);
        System.out.println(openaiKey);
        //请求的模型，最长字符串
        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + question + "\", \"temperature\": 0, \"max_tokens\": 1024}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        String res = EntityUtils.toString(response.getEntity());
        System.out.println(res);

        System.out.println("code: " + response.getStatusLine().getStatusCode());

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getText());
            }
            return answers.toString();
        }else{
            System.out.println("发生什么是");
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());

        }
    }
}
