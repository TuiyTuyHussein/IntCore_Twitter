package dev.hussein.intcoretwitter.config;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.afinal.simplecache.ACache;
import org.json.JSONException;
import org.json.JSONObject;

import dev.hussein.intcoretwitter.pojo.FollowersResponse;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

public class CacheUtility {
    private static final java.lang.String TAG_FOLLOWERS_RESPONSE = "TAG_FOLLOWERS_RESPONSE";
    private final ACache aCache;

    private CacheUtility(Context context) {
        aCache = ACache.get(context);
    }

    public static CacheUtility instance(Context context) {
        return new CacheUtility(context);
    }

    public void cacheFollowersResponse(FollowersResponse followersResponse) {
        Observable.just(saveFollowers(followersResponse))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        aBoolean -> {
                            Log.i("CACHE_TAG", "save follower status -> " + aBoolean);
                        }, error -> {
                            Log.i("CACHE_TAG", "save follower error -> " + error.getMessage());
                        });
    }

    private boolean saveFollowers(FollowersResponse followersResponse) {
        Gson gson = new GsonBuilder().create();
        String item = gson.toJson(followersResponse);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        aCache.put(TAG_FOLLOWERS_RESPONSE, jsonObject);
        return true;
    }


    public Observable<FollowersResponse> getCachedFollowersResponse() {
        return Observable.just(getFollowers());
    }

    private FollowersResponse getFollowers() {
        JSONObject jsonObject = aCache.getAsJSONObject(TAG_FOLLOWERS_RESPONSE);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonObject.toString(), FollowersResponse.class);
    }

}
