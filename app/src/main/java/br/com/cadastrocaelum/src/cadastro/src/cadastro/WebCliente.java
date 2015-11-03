package br.com.cadastrocaelum.src.cadastro.src.cadastro;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Bruno - PC on 31/08/2015.
 */
public class WebCliente {


    private String url;
    public WebCliente(String url) {

        this.url = url;
    }

    public String post(String dadosJSON) {

        try {
            HttpClient client =  new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(new StringEntity(dadosJSON));
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json");

            HttpResponse response = client.execute(httpPost);
            HttpEntity resposta = response.getEntity();

            String respostaJSON = EntityUtils.toString(resposta);

            return respostaJSON;

            }catch (Exception e){
                throw new RuntimeException(e);
         }

    }
}
