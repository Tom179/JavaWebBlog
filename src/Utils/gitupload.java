package Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class gitupload {
    //github的个人token
    public static String token = "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";

    /**
     * 将二进制数据转为base64编码格式数据
     * @param inputStream
     * @return
     * @throws IOException
     */


    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * 拼接url向github发起put请求
     * 接收返回的json解析下载地址
     * @param base64Content
     * @param filePath
     * @param token
     * @return
     * @throws IOException
     */
    public static String uploadToGitHub(String base64Content, String filePath, String token) throws IOException {
        filePath = filePath.replace("\\", "/");

        String repo = "Tom179/imgs"; // GitHub 仓库名
        String uploadUrl = "https://api.github.com/repos/" + repo + "/contents/imgs/" + filePath; // API URL
        System.out.println(uploadUrl);

        // 创建请求体
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Upload file");
        data.put("content", base64Content);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        // 发送 POST 请求到 GitHub
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(uploadUrl);
            request.setHeader("Authorization", "Bearer " + token);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Accept","application/vnd.github+json");
            request.setHeader("X-GitHub-Api-Version","2022-11-28");
            request.setHeader("Accept-Encoding","base64");
            request.setEntity(new StringEntity(json));

            try (CloseableHttpResponse response = client.execute(request)) {
                //获取响应并转为json字符串
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseString);

                // 提取 download_url 字段
                JsonNode downloadUrlNode = rootNode.path("content").path("download_url");
                String downloadUrl = downloadUrlNode.asText();

                System.out.println(downloadUrl);

                return downloadUrl;
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
