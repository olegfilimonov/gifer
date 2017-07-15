package com.olgefilimonov.gifer.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.olgefilimonov.gifer.R;
import com.olgefilimonov.gifer.model.Gif;
import java.util.List;

/**
 * @author Oleg Filimonov
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
  private List<Gif> gifs;
  private Activity activity;
  private SearchAdapterListener searchAdapterListener;

  public SearchResultAdapter(List<Gif> gifs, Activity activity, SearchAdapterListener searchAdapterListener) {
    this.gifs = gifs;
    this.activity = activity;
    this.searchAdapterListener = searchAdapterListener;
  }

  public void updateGifRating(String gifId, int newRating) {
    for (int i = 0; i < gifs.size(); i++) {
      Gif gif = gifs.get(i);
      if (gif.getGifId().equals(gifId)) {
        gif.setScore(newRating);
        notifyItemChanged(i);
        return;
      }
    }
  }

  @Override public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gif, parent, false);
    return new SearchResultViewHolder(view);
  }

  @Override public void onBindViewHolder(final SearchResultViewHolder holder, int position) {
    final Gif gif = gifs.get(position);

    // Load preview image
    Glide.with(activity).load(gif.getPreviewUrl()).into(holder.image);
    // Setup click
    holder.card.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        searchAdapterListener.onItemClick(gif);
      }
    });
    holder.score.setText(String.valueOf(gif.getScore()));
    // Likes & Dislike click
    holder.like.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        searchAdapterListener.onItemRated(gif, 1);
      }
    });

    holder.dislike.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        searchAdapterListener.onItemRated(gif, -1);
      }
    });
  }

  @Override public int getItemCount() {
    return gifs.size();
  }

  public interface SearchAdapterListener {
    void onItemRated(Gif gif, int rating);

    void onItemClick(Gif gif);
  }

  class SearchResultViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.gif_card) CardView card;
    @BindView(R.id.gif_image) ImageView image;
    @BindView(R.id.gif_like) ImageView like;
    @BindView(R.id.gif_dislike) ImageView dislike;
    @BindView(R.id.gif_score) TextView score;

    SearchResultViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
