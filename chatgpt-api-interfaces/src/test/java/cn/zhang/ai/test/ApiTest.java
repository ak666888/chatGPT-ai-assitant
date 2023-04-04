package cn.zhang.ai.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * @description 单元测试
 */
public class ApiTest {
    @Value("${chatai.openAiKey}")
    private String openAiKey;

    @Test
    public  void query_unanswer_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885824421441/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie","zsxq_access_token=F15327C5-60F2-1C54-39AE-7876DA04BDF1_86106D35BE55826D; zsxqsessionid=05fd16fac8780432486d4bb8585483bb; abtest_env=product; UM_distinctid=187468f29f9aa5-04086a8a1f1253-26031851-e1000-187468f29fa1445; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"818542588288182\",\"first_id\":\"187468f2a89cb3-055dd4e0771d918-26031851-921600-187468f2a8a106d\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg3NDY4ZjJhODljYjMtMDU1ZGQ0ZTA3NzFkOTE4LTI2MDMxODUxLTkyMTYwMC0xODc0NjhmMmE4YTEwNmQiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI4MTg1NDI1ODgyODgxODIifQ==\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"818542588288182\"},\"$device_id\":\"187468f2a89cb3-055dd4e0771d918-26031851-921600-187468f2a8a106d\"}");
        get.addHeader("Content-Type","application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void answer() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

//        /v2/groups/28885824421441/topics?scope=unanswered_questions&count=20
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/814282811222452/answer");

        post.addHeader("cookie","zsxq_access_token=F15327C5-60F2-1C54-39AE-7876DA04BDF1_86106D35BE55826D; zsxqsessionid=05fd16fac8780432486d4bb8585483bb; abtest_env=product; UM_distinctid=187468f29f9aa5-04086a8a1f1253-26031851-e1000-187468f29fa1445; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"818542588288182\",\"first_id\":\"187468f2a89cb3-055dd4e0771d918-26031851-921600-187468f2a8a106d\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg3NDY4ZjJhODljYjMtMDU1ZGQ0ZTA3NzFkOTE4LTI2MDMxODUxLTkyMTYwMC0xODc0NjhmMmE4YTEwNmQiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI4MTg1NDI1ODgyODgxODIifQ==\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"818542588288182\"},\"$device_id\":\"187468f2a89cb3-055dd4e0771d918-26031851-921600-187468f2a8a106d\"}");
        post.addHeader("Content-Type","application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"又不会捏！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));

        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void test_gpt() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 代理地址；open.aiproxy.xyz、open2.aiproxy.xyz
        HttpPost post = new HttpPost("https://open2.aiproxy.xyz/v1/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer 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");

        //请求的模型，最长字符串
        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));

        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println("openaikey: "+ openAiKey);
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }
}
