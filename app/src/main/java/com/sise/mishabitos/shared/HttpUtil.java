package com.sise.mishabitos.shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String GET(String baseUrl, String path) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(baseUrl + path);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            return readResponse(con);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }

    public static String POST(String baseUrl, String path, String body) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(baseUrl + path);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            return readResponse(con);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }

    public static String PUT(String baseUrl, String path, String body) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(baseUrl + path);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            return readResponse(con);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }

    public static String DELETE(String baseUrl, String path) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(baseUrl + path);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("DELETE");
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            return readResponse(con);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }

    private static String readResponse(HttpURLConnection con) throws IOException {
        InputStream stream = (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST)
                ? con.getInputStream()
                : con.getErrorStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "utf-8"));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return response.toString();
    }
}
