package app.com.example.rihanna.abookfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.db.*;
import app.com.example.rihanna.abookfinder.utils.*;

/**
 * Created by Rihanna on 15/04/2015.
 */
public class BookSearchFragment extends Fragment
        implements SearchResultReceiver.Receiver,AdapterView.OnItemClickListener
    {
        String bookTitle;
        private ListView mListView;
        private SearchResultReceiver mReceiver;
        ArrayList<Book> bookList;
        private ArrayAdapter<Book> adapter=null;
        String jsonStr="";
        int idBook;
        boolean twoPane;
        ProgressDialog progressDialog;
        public BookSearchFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_book_search, container, false);
           // progressDialog = new ProgressDialog(getActivity(),R.style.TransparentProgressDialog) ;
            bookTitle=getArguments().getString("bookTitle");
            twoPane=getArguments().getBoolean("mTwoPane");
            mListView = (ListView) rootView.findViewById(R.id.listview);
          // progressDialog=(ProgressDialog)rootView.findViewById(R.id.pbLoading);
            final String QUERY_BASE = "https://www.googleapis.com/books/v1/volumes?";
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String PRINT_TYPE = "printType";
            Uri builtUri = Uri.parse(QUERY_BASE).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,bookTitle)
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            final String url= builtUri.toString();
        /* Starting Download Service */
            mReceiver = new SearchResultReceiver(new Handler());
            mReceiver.setReceiver(this);
            Intent intentTo = new Intent(Intent.ACTION_SYNC, null,getActivity(), SearchService.class);
        /* Send optional extras to Download IntentService */
            intentTo.putExtra("url", url);
            intentTo.putExtra("receiver", mReceiver);
            intentTo.putExtra("requestId", 101);
            getActivity().startService(intentTo);
            return rootView;
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException {
            switch (resultCode) {
                case SearchService.STATUS_RUNNING:
                 // getActivity().setSupportProgressBarIndeterminateVisibility(true
                   progressDialog = ProgressDialog.show(getActivity(), null, "Searching!");
                    break;
                case SearchService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                 //   this.setSupportProgressBarIndeterminateVisibility(false);
                   progressDialog.dismiss();
                   bookList=resultData.getParcelableArrayList("result");

                    if(bookList.size()==0||bookList==null){
                        Toast toast = Toast.makeText(getActivity(), "Search has no books for you with. Retry!!!", Toast.LENGTH_SHORT);
                        toast.show();
                    }else if(bookList.size()==1 && bookList.get(0).getTitle().equals("NO BOOK WITH THIS TITLE")){
                        Toast toast = Toast.makeText(getActivity(), "Search has no books for you with. Retry!!!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    if (bookList != null ) {
                        BookListViewAdapter adapter = new BookListViewAdapter(getActivity(),
                                R.layout.fragment_book_search, bookList);
                        mListView.setAdapter(adapter);
                       mListView.setOnItemClickListener(this);
                    }
                /* Update ListView with result */
                    break;
                case SearchService.STATUS_ERROR:
                /* Handle the error */
                    progressDialog.dismiss();
                    //String error = resultData.getString(Intent.EXTRA_TEXT);
                    //Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(), "Please go back and put a book!", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }
        }
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            idBook=position;
            if(!twoPane){
            Intent intent = new Intent(getActivity(), BookDetailView.class);
            intent.putExtra("idBook", position);
            Log.i("intent on Main with " + idBook, "3");
            startActivity(intent);
            }else{
                BookDetailFragment fragment2=new  BookDetailFragment();
                Bundle bundle2=new Bundle();
                bundle2.putInt("idBook",idBook);
                fragment2.setArguments(bundle2);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, fragment2, "DFTAG")
                        .commit();
            }
        }
}
