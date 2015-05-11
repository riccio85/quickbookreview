package app.com.example.rihanna.abookfinder.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import org.json.JSONException;

public class SearchResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public SearchResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData){
        if (mReceiver != null) {
            try {
                mReceiver.onReceiveResult(resultCode, resultData);
            }catch(JSONException e){

            }
        }
    }
}
