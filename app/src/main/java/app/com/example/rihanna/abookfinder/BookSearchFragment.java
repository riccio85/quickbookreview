package app.com.example.rihanna.abookfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.service.SearchResultReceiver;
import app.com.example.rihanna.abookfinder.service.SearchService;
import app.com.example.rihanna.abookfinder.utils.*;


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
            bookTitle=getArguments().getString("bookTitle");
            twoPane=getArguments().getBoolean("mTwoPane");
            mListView = (ListView) rootView.findViewById(R.id.listview);
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
                   progressDialog = ProgressDialog.show(getActivity(), null, "Searching!");
                /*setting progressdialog dissmisble */
                   progressDialog.setCancelable(true);
                    break;
                case SearchService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                   progressDialog.dismiss();
                   bookList=resultData.getParcelableArrayList("result");

                    if(bookList.size()==0||bookList==null){
                        Toast toast = Toast.makeText(getActivity(), "Can't fetch data correctly. Retry!!!", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                        /*case the search has no result */
                    }else if(bookList.size()==1 && bookList.get(0).getMessage().equals("NO BOOK WITH THIS TITLE")){
                        Toast toast = Toast.makeText(getActivity(), "Sorry.Can't find a book with this input.Retry!!!", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                        /*case to much time to serach */
                    }else if(bookList.size()==1 && bookList.get(0).getMessage().equals("SEARCH TAKES TO MUCH TIME")){
                        Toast toast = Toast.makeText(getActivity(), "Search is taking to much time.Retry!!!", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    else {
                        BookListViewAdapter adapter = new BookListViewAdapter(getActivity(),
                                R.layout.fragment_book_search, bookList);
                        mListView.setAdapter(adapter);
                        mListView.setOnItemClickListener(this);
                    }
                    break;
                case SearchService.STATUS_ERROR:
                /* Handle the error */
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(getActivity(), "No search inut. Please go back to put a !", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }
        }

        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            idBook=position;
            if(!twoPane){
            Intent intent = new Intent(getActivity(), BookDetailView.class);
            intent.putExtra("idBook", position);
            startActivity(intent);
            }else{

                /*highlight the selected list item */
                view.getFocusables(position);
                view.setSelected(true);

                /*Show the detailed book */
                FrameLayout frameLT= (FrameLayout)  getActivity().findViewById(R.id.detail_container);
                frameLT.setVisibility(View.VISIBLE);

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
