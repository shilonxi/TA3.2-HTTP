package android.shilon.urlconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class Main_Activity extends AppCompatActivity
{
    private EditText editText;
    private Button button1;
    private Button button2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        editText=findViewById(R.id.edit);
        button1=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        textView=findViewById(R.id.text);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestWithURLConnection();
                //开启子线程
            }
        });
        //用于urlconnection
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestWithHttpURLConnection();
                //开启子线程
            }
        });
        //用于httpurlconnection
    }

    private void sendRequestWithURLConnection()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                URLConnection conn;
                BufferedReader rd;
                //建立新变量
                try
                {
                    URL url=new URL(editText.getText().toString());
                    //类URL的用法
                    conn=url.openConnection();
                    //类URLConnection的用法
                    rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=rd.readLine())!=null)
                    {
                        response.append(line);
                    }
                    //得值
                    showResponse(response.toString());
                    //显示
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendRequestWithHttpURLConnection()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                HttpURLConnection connection;
                BufferedReader reader;
                //建立新变量
                try
                {
                    URL url=new URL(editText.getText().toString());
                    //这里的网址可自选
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    //HttpURLConnection的用法
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //设置
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    //得值
                    showResponse(response.toString());
                    //显示
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                textView.setText(response);
            }
        });
    }

}
