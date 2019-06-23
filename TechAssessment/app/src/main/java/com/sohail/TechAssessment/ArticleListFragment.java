package com.sohail.TechAssessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*Created By Sohail Yasin*/
public class ArticleListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Article> articles;
    ListView articalListView;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        public void onAnimalSelected(Article article);
    }

    OnFragmentInteractionListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*I have put the null value as the second parameter of inflate() method because
         I don't want to attach the inflated View to any ViewGroup now.
          (host Activity of this Fragment will attach the view automatically
          but if the View was attacthed, the exception will thrown)*/
        View view = inflater.inflate(R.layout.fragment_animal_list, null);
        return view;

    }

    AdapterView.OnItemClickListener onAnimalListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Article article = (Article) adapterView.getItemAtPosition(i);
            Log.d("Sohail_article", "onAnimalSelected1: " + article.getDescription() + "   " + article.getName());

            if (listener != null)
                listener.onAnimalSelected(article);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articalListView = (ListView) view.findViewById(android.R.id.list);
        getArticleList();

    }


    private void getArticleList() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("loading");
        pd.show();
        articles = new ArrayList<Article>();
        AndroidNetworking.get(Utils.URL_Article)
                .setPriority(Priority.LOW)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("JsonTest", "GetCarePList: " + response);
                        String status = "", message = "";
                        try {
                            status = response.getString("status");
                            if (status.equals("OK")) {
                                //media
                                JSONArray jsonArray = response.getJSONArray("results");
                                if (jsonArray.length() > 0) {

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Article mObj = new Article();
                                        mObj.setName(jsonObject.getString("title"));
                                        mObj.setDetailUrl(jsonObject.getString("url"));
                                        mObj.setDescription(jsonObject.getString("abstract"));
                                        mObj.setImageURL(jsonObject.getString("abstract"));
                                        mObj.setWriter(jsonObject.getString("byline"));
                                        mObj.setDate(jsonObject.getString("published_date"));

                                        JSONArray jsonArrayM = jsonObject.getJSONArray("media");
                                        if (jsonArrayM.length() > 0) {

                                            for (int j = 0; j < jsonArrayM.length(); j++) {
                                                JSONObject JsonObjectM = jsonArrayM.getJSONObject(j);

                                                if (JsonObjectM.getString("type").equals("image")) {
                                                    JSONArray jsonArrayImage = JsonObjectM.getJSONArray("media-metadata");
                                                    if (jsonArrayImage.length() > 0) {
                                                        JSONObject JsonObjectImage = jsonArrayImage.getJSONObject(0);
                                                        mObj.setImageURL(JsonObjectImage.getString("url"));
                                                    }
                                                }


                                            }
                                        }
                                        articles.add(mObj);


                                    }
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"Something went wrong with api",Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                        if (status.equals("OK")) {
                            pd.dismiss();
                            ArticleListAdapter articleListAdapter = new ArticleListAdapter(getActivity(), articles);
                            articalListView.setAdapter(articleListAdapter);
                            articalListView.setOnItemClickListener(onAnimalListViewItemClickListener);

                        } else {

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Toast.makeText(getContext(),"Something went wrong with api",Toast.LENGTH_LONG).show();

                        Log.d("JsonTest", "GetCarePList: " + anError);
                    }

                });
    }
}
