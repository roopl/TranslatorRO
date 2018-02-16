package cz.utb.fai.translator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.AbstractMultiValuedMap;

import cz.utb.fai.translator.adapter.DictionaryAdapter;
import cz.utb.fai.translator.api.ApiClient;
import cz.utb.fai.translator.api.ApiInterface;
import cz.utb.fai.translator.api.pojo.ResponseData;
import cz.utb.fai.translator.api.pojo.ResponseTranslator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PageHome implements View.OnClickListener{
    private MainActivity mActivity;
    private TextView mResult;
    private Button mTranslate;
    private Button mAddToDictionary;
    private Spinner mInputSpinner;
    private Spinner mOutputSpinner;
    private String[] mAbbrs;
    private EditText mEditText;
    private List<String> mHistory;
    private List<String> mDictionary;
    private HashMap<String,String> pokus;

    //private Map<String,List<String>> mDictionary = new HashMap<String, List<String>>();

    public PageHome(MainActivity activity){
        mActivity = activity;
        mTranslate = (Button)activity.findViewById(R.id.btnTranslate);
        mAddToDictionary = (Button)activity.findViewById(R.id.btnAddToDictionary);
        //mTranslate = (Button)activity.findViewById(R.id.btn);
        mTranslate.setOnClickListener(this);
        mAddToDictionary.setOnClickListener(this);
        mInputSpinner = (Spinner)activity.findViewById(R.id.inputSpinner);
// set default value - Czech
        mInputSpinner.setSelection(0);
        mOutputSpinner = (Spinner)activity.findViewById(R.id.outputSpinner);
// set default value - English
        mOutputSpinner.setSelection(1);
        mAbbrs = activity.getResources().getStringArray(R.array.languages_abbr);
        mResult = (TextView)activity.findViewById(R.id.tvResult);
        mEditText = (EditText) activity.findViewById(R.id.etTranslate);

    }
    @Override
    public void onClick(View view) {
        int inputPos = mInputSpinner.getSelectedItemPosition();
        int outPos = mOutputSpinner.getSelectedItemPosition();
        String translatedText = mEditText.getText().toString();
        List<String> m_words = new ArrayList<String>();
        switch (view.getId()) {
            case R.id.btnTranslate:

                if (translatedText.isEmpty()) {
// empty text
                } else {
// we have translated text from user input
                    Log.v("transApp", translatedText);
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseTranslator> call = apiService.getTranslation(translatedText,
                            mAbbrs[inputPos] + '|' + mAbbrs[outPos]);
                    call.enqueue(new MyCall(translatedText));
                }
                break;
            case R.id.btnAddToDictionary:
                if (!translatedText.isEmpty()) {
                    mActivity.writeToFile(mAbbrs[inputPos] + "To" + mAbbrs[outPos] + ".txt", mActivity.getLastOfHistoryList());
                }
                break;
        }
    }


private class MyCall implements Callback<ResponseTranslator>
{
    private String mTranslatedText;
    private MyCall(String translatedText) {
        mTranslatedText = translatedText;
    }
    @Override
    public void onResponse(Call<ResponseTranslator> call,
                           Response<ResponseTranslator> response) {
        System.out.println("onResponse");
        if (mResult != null){
            ResponseData data = response.body().getResponseData();
            mResult.setText("Překlad: " + data.getTranslatedText());
            mResult.setVisibility(View.VISIBLE);
            mHistory = mActivity.getHistoryList();
            mHistory.add(mTranslatedText + " >> " + data.getTranslatedText());
            RecyclerView.Adapter mAdapterHistory = mActivity.returnHistoryAdapter();
            mAdapterHistory.notifyDataSetChanged();


        }
    }
    @Override
    public void onFailure(Call<ResponseTranslator> call, Throwable t) {
        System.out.println("onFailure");
        t.printStackTrace();
    }
}
}


