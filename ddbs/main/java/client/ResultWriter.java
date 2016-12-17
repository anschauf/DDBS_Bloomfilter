package client;

import au.com.bytecode.opencsv.CSVWriter;
import shared.Constants;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Andy on 17.12.16.
 */
public class ResultWriter {

    public void writeJoinResult(JoinResult[][] results) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(Constants.csvOutput), ',');

            int mSize = results.length;
            int kSize = results[0].length;


            //header
            String[] header  = new String[mSize*4+1];
            header[0] = "K \\ M";
            for (int m = 0; m < mSize; m++) {
                header[m*4+1] = Integer.toString(results[m][0].getBloomFilterSize());
                header[m*4+2] = "";
                header[m*4+3] = "";
                header[m*4+4] = "";

            }
            writer.writeNext(header);

            //k and body(False-Positives)
            for (int k = 0; k < kSize; k++) {
                String[] body = new String[mSize*4+1];
                body[0] = Integer.toString(results[0][k].getHashes());
                for(int m = 0; m < (mSize); m++) {
                    body[m*4+1] = Integer.toString(results[m][k].getSetToTrue1());
                    body[m*4+2] = Integer.toString(results[m][k].getSetToTrue2());
                    body[m*4+3] = Integer.toString(results[m][k].getSetToTrue3());
                    body[m*4+4] = Integer.toString(results[m][k].getFalsePositives());
                }
                writer.writeNext(body);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}