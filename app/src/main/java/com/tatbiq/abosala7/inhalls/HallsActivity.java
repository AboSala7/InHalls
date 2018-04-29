package com.tatbiq.abosala7.inhalls;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tatbiq.abosala7.inhalls.hallsearchadapter.HallAdapter;
import com.tatbiq.abosala7.inhalls.hallsearchadapter.InHallsData;
import com.tatbiq.abosala7.inhalls.login.SignIn;
import com.tatbiq.abosala7.inhalls.parsingapi.ApiClient;
import com.tatbiq.abosala7.inhalls.parsingapi.ApiInterface;
import com.tatbiq.abosala7.inhalls.registerationDb.SqliteHelper;
import com.tatbiq.abosala7.inhalls.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HallsActivity extends AppCompatActivity {

    List<InHallsData> halls = new ArrayList<>();
    InHallsData hall;
    RecyclerView recyclerView;
    HallAdapter hallAdapter;
    SearchView searchView;
    String jsonString ;
    Context context;
    ProgressDialog pDialog;
    SqliteHelper db;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halls);
        db = new SqliteHelper(this);
        if(!db.checkUserExist()){
            intent = new Intent(HallsActivity.this, SignIn.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"مرحبا "+db.getSavedUser(),Toast.LENGTH_LONG).show();
        }
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setTitle("Tool bar");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        pDialog = new ProgressDialog(HallsActivity.this);
        pDialog.setMessage("Loading Data...");
        pDialog.setCancelable(true);
        pDialog.show();



        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


       Call<List<InHallsData>> call = apiService.getAllHalls();
        call.enqueue(new Callback<List<InHallsData>>() {


            @Override
            public void onResponse(Call<List<InHallsData>> call, Response<List<InHallsData>> response) {

                halls = response.body();
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                hallAdapter = new HallAdapter(halls, getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(hallAdapter);
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<InHallsData>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"يوجد خطأ باللإتصال",Toast.LENGTH_LONG).show();
                Log.i("t==========",t.toString());

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                hallAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                hallAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    // if the api returns as a string

 /*  Call<String>call = apiService.getStringData();
   call.enqueue(new Callback<String>() {
       @Override
       public void onResponse(Call<String> call, Response<String> response) {
           String jsonResponse = response.body();
           try {
               halls = getInHallDataArray(jsonString,halls);
           } catch (JSONException e) {
               e.printStackTrace();
           }

           pDialog.dismiss();
           RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
           recyclerView.setLayoutManager(mLayoutManager);
           recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
           recyclerView.setItemAnimator(new DefaultItemAnimator());
           recyclerView.setAdapter(hallAdapter);
       }
       @Override
       public void onFailure(Call<String> call, Throwable t) {
           pDialog.dismiss();
           Log.i("failure=====",t.toString());
       }
   });

*/
 /*   public static List<InHallsData> getInHallDataArray(String response ,List<InHallsData> myList) throws JSONException {

        HashMap<String,String> map = new HashMap<>();
        map.put("key",response);
        JSONObject jsonObject = new JSONObject(map.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("key");

        for(int x = 0 ; x <= jsonArray.length() ; x++){
            InHallsData inHallData = new InHallsData((jsonArray.getJSONObject(x).getString("hallID")),
                    (jsonArray.getJSONObject(x).getString("HallName")),
                    (jsonArray.getJSONObject(x).getString("Capacity")),
                    (jsonArray.getJSONObject(x).getString("MenCapacity")),
                    (jsonArray.getJSONObject(x).getString("WomenCapacity")),
                    (jsonArray.getJSONObject(x).getString("ImgPath")),
                    new String[]{(jsonArray.getJSONObject(x).getString("Photos"))},
                    new String[]{(jsonArray.getJSONObject(x).getString("Services"))});

            myList.add(inHallData);

        }
        return myList;
        }
 */
}
