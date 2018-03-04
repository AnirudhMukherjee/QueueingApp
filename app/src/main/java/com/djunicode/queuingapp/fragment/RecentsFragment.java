package com.djunicode.queuingapp.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.adapter.RecentsAdapter;
import com.djunicode.queuingapp.customClasses.RecyclerItemTouchHelper;
import com.djunicode.queuingapp.customClasses.RecyclerItemTouchHelper.RecyclerItemTouchHelperListener;
import com.djunicode.queuingapp.model.RecentEvents;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentsFragment extends Fragment implements
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    Bundle deletedStuff;
    private RecyclerView recentsRecyclerView;
    private RecentsAdapter adapter;
    private RelativeLayout relativeLayout;
    List<RecentEvents> recentEventsList = TeacherSubmissionFragment.recentEventsList;
    public static List<Integer> eventIds = new ArrayList<>();

    public RecentsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_recents, container, false);

    deletedStuff = new Bundle();
    recentsRecyclerView = (RecyclerView) view.findViewById(R.id.recentsRecyclerView);
    recentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new RecentsAdapter(getContext(), recentEventsList);
    relativeLayout = (RelativeLayout) view.findViewById(R.id.recentsRelativeLayout);

    recentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    recentsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
        DividerItemDecoration.VERTICAL));
    recentsRecyclerView.setAdapter(adapter);

    ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,
        ItemTouchHelper.LEFT, this);
    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recentsRecyclerView);

    return view;
  }

  @Override
  public void onSwiped(ViewHolder viewHolder, int direction, final int position) {

    if(viewHolder instanceof RecentsAdapter.MyViewHolder){
      final RecentEvents event = TeacherSubmissionFragment.recentEventsList
          .get(viewHolder.getAdapterPosition());

        deletedStuff.putString("subject",event.getSubjectName());
        deletedStuff.putInt("size",event.getSize());
        deletedStuff.putString("from",event.getStartTime());
        deletedStuff.putString("to",event.getEndTime());
        deletedStuff.putInt("ids",eventIds.get(position));
//        Call<RecentEvents> call = apiInterface.getQueueId(event.getId());
//        call.enqueue(new Callback<RecentEvents>() {
//            @Override
//            public void onResponse(Call<RecentEvents> call, Response<RecentEvents> response) {
//                Log.d("Recent",Integer.toString(response.body().getId()));
//                 sendDatatoServer(response.body().getId());
//            }
//
//            @Override
//            public void onFailure(Call<RecentEvents> call, Throwable t) {
//
//            }
//        });
        Call<RecentEvents> call = apiInterface.deleteRecentEvent(eventIds.get(position));
        call.enqueue(new Callback<RecentEvents>() {
            @Override
            public void onResponse(Call<RecentEvents> call, Response<RecentEvents> response) {
                Log.d("Delete event","Event deleted from the list");
                Log.d("Idno",Integer.toString(eventIds.get(position)));
            }

            @Override
            public void onFailure(Call<RecentEvents> call, Throwable t) {

            }
        });
      final int deletedIndex = viewHolder.getAdapterPosition();

      adapter.removeItem(viewHolder.getAdapterPosition());


      Snackbar snackbar = Snackbar
          .make(relativeLayout, "Removed from recents!", Snackbar.LENGTH_LONG);
      snackbar.setAction("UNDO", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          adapter.restoreItem(event, deletedIndex);
          Call<TeacherCreateNew> call = apiInterface.sendSubmissionData(deletedStuff.getString("subject"),deletedStuff.getInt("size"),"Sheku", deletedStuff.getString("from")+":00",deletedStuff.getString("to")+":00","");
          call.enqueue(new Callback<TeacherCreateNew>() {
              @Override
              public void onResponse(Call<TeacherCreateNew> call, Response<TeacherCreateNew> response) {
                    Log.d("addback","event added back!");
              }

              @Override
              public void onFailure(Call<TeacherCreateNew> call, Throwable t) {

              }
          });
        }
      });
      snackbar.setActionTextColor(Color.YELLOW);
      snackbar.show();
      eventIds.remove(position);
    }
  }

//  public void sendDatatoServer(int id){
//      Call<RecentEvents> call = apiInterface.deleteRecentEvent(id);
//      call.enqueue(new Callback<RecentEvents>() {
//          @Override
//          public void onResponse(Call<RecentEvents> call, Response<RecentEvents> response) {
//            Log.d("Delete event","Event deleted from the list");
//          }
//
//          @Override
//          public void onFailure(Call<RecentEvents> call, Throwable t) {
//
//          }
//      });
//  }
}


