package com.jalaj.firstapp.externalfilereader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mBtn1, mBtn2;
    TextView mTxtVw1;
    EditText mEdTxt1;
    boolean writing = false;
    File fileIp, fileOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn1 = (Button)findViewById(R.id.mBtn1);
        mBtn2 = (Button)findViewById(R.id.mBtn2);
        mTxtVw1 = (TextView)findViewById(R.id.mTxtVw1);
        mEdTxt1 = (EditText)findViewById(R.id.mEdTxt1);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.mBtn1)
        new WriteToTextFile(mEdTxt1.getText().toString()).execute();
        else
        if ((!writing) && (v.getId() == R.id.mBtn2)) new DeleteTextFile().execute();

    }

    class WriteToTextFile extends AsyncTask<String, String, String>{

        private String content;
         WriteToTextFile (String content)
        {
            this.content = content;
            publishProgress(content);

        }
        @Override
        protected String doInBackground(String... params) {

            FileOutputStream fileOutputStream = null;
            fileOp = new File("/storage/emulated/0/Acadglid/testfile.txt");


            if (!fileOp.exists()) {
                try {
                    fileOp.createNewFile();
                    Log.i("here","Not Exists");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileWriter fw = null;
            try {
                fw = new FileWriter(fileOp.getAbsoluteFile(),true);
                Log.i("here","Writing");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(content);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileIp = new File("/storage/emulated/0/Acadglid/testfile.txt");
            String textLine, finalText="";
            try {
                FileReader fr = new FileReader(fileIp.getAbsoluteFile());
                BufferedReader br = new BufferedReader(fr);
                while ((textLine = br.readLine()) != null) {
                    finalText = finalText + "\n"+ textLine;
                }
                br.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return finalText;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            writing = true;
            mEdTxt1.setText("");

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTxtVw1.setText(s);
            writing = false;
        }
    }

    class DeleteTextFile extends AsyncTask <String, String, String>
    {
DeleteTextFile(){
    mTxtVw1.setText("");
}
        @Override
        protected String doInBackground(String... params) {

            FileOutputStream fileOutputStream = null;
            fileOp = new File("/storage/emulated/0/Acadglid/testfile.txt");


            if (fileOp.exists()) {
                fileOp.delete();
                Log.i("here"," Deleting...");
            }
            return null;
        }
    }
}
