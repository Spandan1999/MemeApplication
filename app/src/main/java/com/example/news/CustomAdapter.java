package com.example.news;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.service.chooser.ChooserTarget;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.nativead.MediaView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final ArrayList<String> localDataSet;
    public CustomAdapter(ArrayList<String> dataSet){
        localDataSet = dataSet;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        //View view2 = LayoutInflater.from(parent.getContext()).inflate(com.google.android.ads.nativetemplates.R.layout.gnt_medium_template_view,parent,false);
        //NativeAdViewHolder viewHolder2 = new NativeAdViewHolder(view2);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, localDataSet.get(viewHolder.getAdapterPosition()));
                        intent.setType("text/plain");
                        Intent chooser = Intent.createChooser(intent,"Share this meme using..");
                        view.getContext().startActivity(chooser);
                    }
                });
                return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //switch (getItemViewType(position)) {
            //case 0:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Glide.with(itemViewHolder.getImageView()).load(localDataSet.get(position)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        itemViewHolder.getProgressBar().setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        itemViewHolder.getProgressBar().setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).into(itemViewHolder.getImageView());
        }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    /*
    @Override
    public int getItemViewType(int position){
        int res = 0;
        if(position == 4)
            res = 1;
        return res;
    }
    */
    public void update(ArrayList<String> memes)
    {
        localDataSet.addAll(memes);
        notifyItemRangeChanged(localDataSet.size()-4,localDataSet.size()-1);
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final ProgressBar progressBar;
        private final TextView textView;
        public ItemViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageView = view.findViewById(R.id.imageView);
            progressBar= view.findViewById(R.id.progressBar);
            textView = view.findViewById(R.id.textView);
        }

        public ImageView getImageView() {
            return imageView;
        }
        public ProgressBar getProgressBar(){
            return progressBar;
        }
        public TextView getTextView(){return textView;}
    }
    /*
    This below code is for binding the Native ad view to the recycler view
    public static class NativeAdViewHolder extends RecyclerView.ViewHolder{
        private final MediaView mediaView;
        private final ImageView imageView;
        private final ConstraintLayout content;
        private final TextView headLine;
        private final TextView primary;
        private final LinearLayout rowTwo;
        private final TextView adTag;
        private final RatingBar ratingBar;
        private final TextView secondary;
        private final TextView body;
        private final AppCompatButton cta;

        public NativeAdViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaView = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.media_view);
            imageView = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.icon);
            content = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.content);
            headLine = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.headline);
            primary = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.primary);
            rowTwo = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.row_two);
            adTag = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.ad_notification_view);
            ratingBar = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.rating_bar);
            secondary = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.secondary);
            body = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.body);
            cta = itemView.findViewById(com.google.android.ads.nativetemplates.R.id.cta);
        }

        public View getMediaView() {
            return mediaView;
        }
        public View getImageView(){
            return imageView;
        }

        public View getContent() {
            return content;
        }

        public View getAdTag() {
            return adTag;
        }

        public View getBody() {
            return body;
        }

        public View getCta() {
            return cta;
        }

        public View getPrimary() {
            return primary;
        }

        public View getHeadLine() {
            return headLine;
        }

        public View getRatingBar() {
            return ratingBar;
        }

        public View getRowTwo() {
            return rowTwo;
        }

        public View getSecondary() {
            return secondary;
        }
    }
     */
}
