package dev.hussein.intcoretwitter.config;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    private int current_page = 1;

    private RecyclerView.LayoutManager layoutManager;
    private boolean loadMore = true;

    public EndlessRecyclerOnScrollListener(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(this);
        this.layoutManager = recyclerView.getLayoutManager();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!loadMore) return;
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] positions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(positions);
            firstVisibleItem = positions[0];
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);

    public void stop() {
        this.loadMore = false;
    }

    public void play() {
        this.loadMore = true;
    }
}