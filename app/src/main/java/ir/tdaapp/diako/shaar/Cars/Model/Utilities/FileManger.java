package ir.tdaapp.diako.shaar.Cars.Model.Utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileManger {

  public class FileResponse {
    private int code;
    private String response;

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getResponse() {
      return response;
    }

    public void setResponse(String response) {
      this.response = response;
    }
  }

  public static final String FILE_ERROR = "Error File Not Found";
  public static final String FILE_NOT_FOUND = "File Not Found";
  public static final String URL_ERROR = "URL Error";
  public static final String IO_ERROR = "Cannot Read/Write File!";

  private String SERVER_URL;

  public FileManger(String SERVER_URL) {
    this.SERVER_URL = SERVER_URL;

  }

  public FileResponse uploadFile(String selectedFilePath) {
    FileResponse response = new FileResponse();
    String result = "Error";
    int serverResponseCode = 0;

    HttpURLConnection connection;
    DataOutputStream dataOutputStream;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";


    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;
    File selectedFile = new File(selectedFilePath);

    String[] parts = selectedFilePath.split("/");
    final String fileName = parts[parts.length - 1];

    if (!selectedFile.isFile()) {
      response.setCode(400);
      response.setResponse(FILE_ERROR);
      return response;
    } else {
      try {
        FileInputStream fileInputStream = new FileInputStream(selectedFile);

        URL url = new URL(SERVER_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);//Allow Inputs
        connection.setDoOutput(true);//Allow Outputs
        connection.setUseCaches(false);//Don't use a cached Copy
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("ENCTYPE", "multipart/form-data");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        connection.setRequestProperty("uploaded_file", selectedFilePath);
        dataOutputStream = new DataOutputStream(connection.getOutputStream());

        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
          + selectedFilePath + "\"" + lineEnd);

        dataOutputStream.writeBytes(lineEnd);

        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
          dataOutputStream.write(buffer, 0, bufferSize);
          bytesAvailable = fileInputStream.available();
          bufferSize = Math.min(bytesAvailable, maxBufferSize);
          bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


        serverResponseCode = connection.getResponseCode();
        String serverResponseMessage = connection.getResponseMessage();
        if (serverResponseCode != 200) {
          response.setCode(serverResponseCode);
          response.setResponse(serverResponseMessage);
          return response;
        }
        //get return msg from server
        InputStream inputStream = null;
        inputStream = connection.getInputStream();
        result = this.convertStreamToString(inputStream);
        inputStream.close();
        // result=serverResponseMessage;
        //closing the input and output streams
        fileInputStream.close();
        dataOutputStream.flush();
        dataOutputStream.close();


      } catch (FileNotFoundException e) {
        response.setCode(400);
        response.setResponse(FILE_NOT_FOUND);
        return response;
      } catch (MalformedURLException e) {
        response.setCode(400);
        response.setResponse(URL_ERROR);
        return response;
      } catch (IOException e) {
        response.setCode(400);
        response.setResponse(IO_ERROR);
        return response;
      }
      //  dialog.dismiss();
      response.setCode(serverResponseCode);
      response.setResponse(result);
      return response;
    }

  }

  private String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }


}
