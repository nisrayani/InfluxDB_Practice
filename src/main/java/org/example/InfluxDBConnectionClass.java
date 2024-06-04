package org.example;

import java.time.Instant;
import  java.util.*;
import java.util.List;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;


public class InfluxDBConnectionClass {
    private String url;
    private String bucket;
    private String org;
    private String token;


    public InfluxDBClient connection(String url, String token, String org, String bucket){
        setUrl(url);
        setBucket(bucket);
        setOrg(org);
        setToken(token);

        return InfluxDBClientFactory.create(getUrl(),getToken().toCharArray(),getOrg(),getBucket());
    }

    //    getters and setters
    public void setOrg(String org) {
        this.org = org;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public String getBucket() {
        return bucket;
    }

    public String getOrg() {
        return org;
    }

    public String getToken() {
        return token;
    }


    public boolean writePoints(InfluxDBClient influxDBClient){
        boolean flag= false;
        try{
            WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();

            Point point1 = Point.measurement("nature")
                    .addTag("location","1")
                    .addField("honeybees",1)
                    .addField("butterflies",1);
            Point point2 = Point.measurement("nature")
                    .addTag("location","2")
                    .addField("honeybees",2)
                    .addField("butterflies",2);
            Point point3 = Point.measurement("nature")
                    .addTag("location","3")
                    .addField("honeybees",3)
                    .addField("butterflies",3);

            System.out.println("point3:"+point3.toLineProtocol());

            List<Point>pointList=new ArrayList<>();
            pointList.add(point1);
            pointList.add(point2);
            pointList.add(point3);

            writeApiBlocking.writePoints(pointList);

            flag=true;
        }catch (InfluxException e){
            System.out.println(e.getMessage());
        }

        return flag;
    }



    public boolean writeMultipleValues(InfluxDBClient influxDBClient) {
        boolean flag= false;

        try{
            WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();
            List<Point>pointList=new ArrayList<>();

            for(int butterfly_count = 10; butterfly_count < 20; butterfly_count++){


                Point point = Point.measurement("nature")
                        .addTag("location","1")
                        .addField("butterflies",butterfly_count)
                        .time(Instant.now().toEpochMilli(),WritePrecision.MS);

                pointList.add(point);

                System.out.println("created point "+ point.toLineProtocol());

                Thread.sleep(1);
            }

            writeApiBlocking.writePoints(pointList);

            System.out.println("Written points");

            flag=true;
        }catch (InfluxException | InterruptedException e){
            System.out.println(e.getMessage());
        }
        return flag;
    }


    public boolean writeButterflyCounts(InfluxDBClient influxDBClient, int[] butterflyCounts) {
        boolean flag = false;

        try {
            WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();
            List<Point> points = new ArrayList<>();

            for (int count : butterflyCounts) {
                Instant now = Instant.now();
                long timestamp = now.toEpochMilli(); // Get the current time in milliseconds

                Point point = Point.measurement("nature")
                        .addTag("location", "1") // Assuming location tag is always "1"
                        .addField("butterflies", count)
                        .time(timestamp, WritePrecision.MS);  // Use the timestamp

                points.add(point);

                // Debugging statement to verify the points being created
                System.out.println("Created point: " + point.toLineProtocol() + " at timestamp: " + timestamp);

                // Small delay to ensure unique timestamps
                Thread.sleep(1);  // 1 millisecond delay
            }

            // Write all points at once
            writeApiBlocking.writePoints(points);

            // Add a statement to confirm writing
            System.out.println("Points written to InfluxDB successfully.");

            flag = true;
        } catch (InfluxException e) {
            System.out.println("InfluxException: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Thread sleep interrupted: " + e.getMessage());
        }

        return flag;
    }

}
