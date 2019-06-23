package com.sohail.TechAssessment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity implements ArticleListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null)
        return;
        ArticleListFragment articleListFragment = new ArticleListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout_left, articleListFragment);

        if(findViewById(R.id.framelayout_right)!=null){
            /*Article article = new Article();
            article.setName("Penguin");
            article.setDetailUrl("http://www.emperor-penguin.com/penguin-chick.jpg");
            article.setDescription("Penguin Penguin Penguin Penguin Penguin Penguin Penguin Penguin Penguin Penguin Penguin Penguin Penguin ");

            Bundle bundle = new Bundle();
            bundle.putSerializable("article", article);

            ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();

            articleDetailFragment.setArguments(bundle);

            fragmentTransaction.add(R.id.framelayout_right, articleDetailFragment);*/
        }

        fragmentTransaction.commit();

    }

    @Override
    public void onAnimalSelected(Article article) {
        Log.d("Sohail_article", "onAnimalSelected: "+article.getDescription()+"   "+article.getName());
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int containerViewId = R.id.framelayout_left;

        if(findViewById(R.id.framelayout_right)!=null)
            containerViewId = R.id.framelayout_right;

        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);

        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        articleDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(containerViewId, articleDetailFragment);
        if(findViewById(R.id.framelayout_right)==null)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
