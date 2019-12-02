package com.example.bacelonatours;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bacelonatours.model.BarceloninaResponse;
import com.example.bacelonatours.model.Tour;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TourListFragment extends Fragment  {

    TourAdapter tourAdapter;
    MainViewModel mainViewModel;
    List<Tour> tourList = new ArrayList<>();

    public TourListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.itemList);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        tourAdapter = new TourAdapter();
        recyclerView.setAdapter(tourAdapter);

        mainViewModel.obtenerTours().observe(this, new Observer<BarceloninaResponse>() {
            @Override
            public void onChanged(BarceloninaResponse barceloninaResponse) {
                tourList =  barceloninaResponse.tours;
                tourAdapter.notifyDataSetChanged();
            }
        });
    }

    class TourViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc;
        ImageView imageItem;

        TourViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tour_name);
            desc = itemView.findViewById(R.id.textImage);


            imageItem = itemView.findViewById(R.id.tour_image);

        }
    }

    class TourAdapter extends RecyclerView.Adapter<TourViewHolder>{

        @NonNull
        @Override
        public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TourViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tour, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final TourViewHolder holder, final int position) {

            final Tour tour = tourList.get(position);

            //holder.name.setText(tour.tourName);
            holder.desc.setText(tour.tourDescription);
            Log.e("ABCD", " lala " + tour.tourImages);
            Glide.with(requireActivity()).load(tour.tourImages).into(holder.imageItem);






                if (tour.tourName != null) {
                    holder.name.setText(tour.tourName);
                    //Glide.with(requireActivity()).load(tour.tourImages.get(i)).into(holder.imagenArray);
                   // Glide.with(requireActivity()).load(tour.tourImages).into(holder.imageItem);
                } else {
                    holder.name.setVisibility(TextView.GONE);
//                    holder.desc.setVisibility(TextView.GONE);
//                    holder.imageItem.setVisibility(View.GONE);
                }
//
//                holder.imageItem.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        mainViewModel.tourId = tour.tourId;
//                        String s = "POR QUÉ TOCAS ?";
//                        new AlertDialog.Builder(requireContext()).setTitle(tour.tourName)
//                                .setMessage("precio tal y cual")
//                                .setCancelable(true)
//                                .create()
//                                .show();
//                        return true;
//                    }
//              });

            holder.imageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.tour.setValue(tour);
//                    Snackbar snackbar = Snackbar
//                            .make(requireView(), "Mensaje a mostrar", Snackbar.LENGTH_LONG);
//                    snackbar.show();

                    Navigation.findNavController(view).navigate(R.id.detailFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tourList.size();
        }
    }
}
