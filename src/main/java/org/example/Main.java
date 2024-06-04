package org.example;

import com.influxdb.client.InfluxDBClient;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        connection
        String url="http://localhost:8086";
        String token="0e_cw-4BJEDzLOcVLI6kVeKv_jLt9Xb2boGErrKXbUzr_iGlQBAGNlMWK25xqzSBw2Szdbt1TuCT5VUdxVhHPQ==";
        String org="flipkart";
        String bucket="test3";
        InfluxDBConnectionClass db = new InfluxDBConnectionClass();
        InfluxDBClient client= db.connection(url,token,org,bucket);

//        boolean writePoints=db.writePoints(client);
//        if(writePoints){
//            System.out.println("Write points successfully");
//        }

        boolean writeMultipleValues=db. writeMultipleValues(client);
        if(writeMultipleValues){
            System.out.println("Written multiple values");
        }

//        int[] butterflyCounts = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
//        boolean writetoInflux= db.writeButterflyCounts(client,butterflyCounts);
//        if(writetoInflux){
//            System.out.println("Written Butterfly Counts in influxDB");
//        }
    }
}