package com.telugupanchangam.telugucalender.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;
import com.telugupanchangam.telugucalender.Adapters.HwAdapter;
import com.telugupanchangam.telugucalender.Adapters.PanchangamTabAdapter;
import com.telugupanchangam.telugucalender.Model.PanchangamTab;
import com.telugupanchangam.telugucalender.helper.ApiConfig;
import com.telugupanchangam.telugucalender.helper.Constant;
import com.telugupanchangam.telugucalender.helper.DatabaseHelper;
import com.telugupanchangam.telugucalender.Model.HomeCollection;
import com.telugupanchangam.telugucalender.R;
import com.telugupanchangam.telugucalender.XmlRecords;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class panchangamFrag extends Fragment {


    public static String selectedGridDate;
    public static int cutmonth;
    RecyclerView recyclerView,recyclerView2;
    Activity activity;
    PanchangamTabAdapter panchangamTabAdapter;


    private static GregorianCalendar cal_month,cal_month_copy;
    private HwAdapter hwAdapter;
    private static int currentMonth;
    private java.util.Calendar cmonth;
    private GridView gridview;
    Uri bitmapUri;

    private String loadXmlFile;
    private ArrayList<XmlRecords> records;
    private boolean changed;
    private int clickedDate;
    private String dateFormat;
    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    DatabaseHelper databaseHelper;

    private TextView sunTxt,moonTxt,sunrise,sunset,moonrise,moonset;
    private TextView tv_month;
    private String[] month = {"???????????????", "????????????????????????", "??????????????????", "?????????????????????", " ?????? ", "????????????", "???????????????", "??????????????????", "??????????????????????????????", "????????????????????????", "??????????????????", "????????????????????????"};
    private String[] monthE = {"January", "February", "March", "Aprial", "May", "June", "July", "August", "September", "October", "November", "December"};

    private UnifiedNativeAd nativeAd;

    public panchangamFrag() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_panchangam, container, false);


        activity = getActivity();


        AdView mAdView = root.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


//        refreshAd();

        databaseHelper = new DatabaseHelper(activity);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));



        HomeCollection.date_collection_arr=new ArrayList<HomeCollection>();

        //**********2019 Holidays **************


//
//        HomeCollection.date_collection_arr.add( new HomeCollection("2021-11-04" ,"diwali","* ????????? ???????????????????????? (?????????????????????) *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2021-12-25" ,"maxres","*  ??????????????????????????????  *"));
//
//
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-01-01" ,"bhogi","* ???????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-01-14" ,"pongal","* ????????? ??????????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-01-26" ,"republic","* ??????????????????????????? ?????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-03-01" ,"shivaratri","* ???????????????????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-03-18" ,"holi","* ????????????  ????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-04-02" ,"ugadi"," * ??????????????? ??????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-04-10" ,"ramnavami","* ????????????????????? ???????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-04-15" ,"friday","* ???????????? ??????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-05-01" ,"mayday"," * ??????????????????????????? ???????????????????????????   *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-05-03" ,"Idul Fitr"," * ??????????????????????????? ???????????????????????????   *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-07-10" ,"bakrid","* ?????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-08-15" ,"independence","* ????????????????????????????????? ??????????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-08-31" ,"varalaxmi","* ??????????????????????????? ???????????????   *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-08-18" ,"janmashtami","* ?????????????????????????????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-08-09" ,"muharram","* ?????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-09-05" ,"teachersday","* ?????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-10-02" ,"gandhi","* ??????????????? ??????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-10-03" ,"astami","*  ???????????????????????? *\n* ?????????????????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-10-05" ,"durga","* ???????????? ???????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-10-24" ,"diwali","* ????????? ???????????????????????? (?????????????????????) *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-12-25" ,"maxres","*  ??????????????????????????????  *"));
//
//
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-01-01" ,"bhogi","* ???????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-01-14" ,"pongal","* ????????? ??????????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-01-26" ,"republic","* ??????????????????????????? ?????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-02-18" ,"shivaratri","* ???????????????????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-03-07" ,"holi","* ????????????  ????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-03-22" ,"ugadi"," * ??????????????? ??????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-03-30" ,"ramnavami","* ????????????????????? ???????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-04-07" ,"friday","* ???????????? ??????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-04-22" ,"idulfitr","* ?????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-05-01" ,"mayday"," * ??????????????????????????? ???????????????????????????   *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-06-29" ,"bakrid","* ?????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-07-29" ,"bakrid","* ?????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-08-15" ,"independence","* ????????????????????????????????? ??????????????????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-09-07" ,"janmashtami","* ?????????????????????????????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-09-19" ,"ganesh","* ?????????????????? ???????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-09-05" ,"teachersday","* ?????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-10-02" ,"gandhi","* ??????????????? ??????????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-10-22" ,"astami","*  ???????????????????????? *\n* ?????????????????????????????????  *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-10-24" ,"durga","* ???????????? ???????????? *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-11-12" ,"diwali","* ????????? ???????????????????????? (?????????????????????) *"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-12-25" ,"maxres","*  ??????????????????????????????  *"));
//
//
//
//
//        //***********Amavasya Punnami Dates***************
//
//        HomeCollection.date_collection_arr.add( new HomeCollection("2021-11-19" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2021-11-04" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2021-12-19" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2021-12-04" ,"","Amayasya"));
//
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-01-17" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-01-02" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-02-16" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-02-01" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-03-18" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-03-02" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-04-16" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-04-01" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-04-30" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-05-16" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-05-30" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-06-14" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-06-29" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-07-13" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-07-28" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-08-12" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-08-27" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-09-10" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-09-25" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-10-09" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-10-25" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-11-08" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-11-23" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-12-08" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2022-12-23" ,"","Amayasya"));
//
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-01-06" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-01-21" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-02-05" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-02-20" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-03-07" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-03-21" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-04-06" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-04-20" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-05-05" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-05-19" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-06-04" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-06-18" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-07-03" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-07-17" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-08-01" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-08-31" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-08-16" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-09-29" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-09-14" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-10-28" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-10-14" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-11-27" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-11-13" ,"","Amayasya"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-12-26" ,"","Purnami"));
//        HomeCollection.date_collection_arr.add( new HomeCollection("2023-12-12" ,"","Amayasya"));

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(getActivity(), cal_month,HomeCollection.date_collection_arr);
        currentMonth = cal_month.get(GregorianCalendar.MONTH);
        cutmonth = cal_month.get(GregorianCalendar.MONTH);

        TextView tv_week1 = root.findViewById(R.id.textView1);
        TextView tv_week2 = root.findViewById(R.id.TextView01);
        TextView tv_week3 = root.findViewById(R.id.TextView02);
        TextView tv_week4 = root.findViewById(R.id.TextView03);
        TextView tv_week5 = root.findViewById(R.id.TextView04);
        TextView tv_week6 = root.findViewById(R.id.TextView05);
        TextView tv_week7 = root.findViewById(R.id.TextView06);
        sunTxt = root.findViewById(R.id.sunrise);
        moonTxt = root.findViewById(R.id.moonrise);
        sunrise = root.findViewById(R.id.sunriseT);
        sunset = root.findViewById(R.id.sunset);
        moonrise = root.findViewById(R.id.moonriseT);
        moonset = root.findViewById(R.id.moonset);


        tv_month = root.findViewById(R.id.tv_month);
        ImageButton previousMonth = root.findViewById(R.id.ib_prev);
        ImageButton nextMonth = root.findViewById(R.id.Ib_next);

        tv_month.setText(month[currentMonth]+" - "+cal_month.get(GregorianCalendar.YEAR));
        loadXmlFile = monthE[currentMonth]+"_"+calendar.get(Calendar.YEAR);
        if(cal_month.get(GregorianCalendar.YEAR) == 2021)
        {
            dateFormat = calendar.get(Calendar.DATE)+" - "+month[currentMonth]+" - "+calendar.get(Calendar.YEAR)+" - ????????????????????????????????? ????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth <3)
        {
            dateFormat = calendar.get(Calendar.DATE)+" - "+month[currentMonth]+" - "+calendar.get(Calendar.YEAR)+" - ????????????????????????????????? ????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth >3)
        {
            dateFormat = calendar.get(Calendar.DATE)+" - "+month[currentMonth]+" - "+calendar.get(Calendar.YEAR)+" - ?????????????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth <3 && calendar.get(Calendar.DATE)<21)
        {
            dateFormat = calendar.get(Calendar.DATE)+" - "+month[currentMonth]+" - "+calendar.get(Calendar.YEAR)+" - ?????????????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth >2)
        {
            dateFormat = calendar.get(Calendar.DATE)+" - "+month[currentMonth]+" - "+calendar.get(Calendar.YEAR)+" -  ?????????????????????" ;
        }


        clickedDate = (calendar.get(Calendar.DATE)-1);
        selectedGridDate = "";

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/sree.ttf");
        tv_week1.setTypeface(typeface);
        tv_week2.setTypeface(typeface);
        tv_week3.setTypeface(typeface);
        tv_week4.setTypeface(typeface);
        tv_week5.setTypeface(typeface);
        tv_week6.setTypeface(typeface);
        tv_week7.setTypeface(typeface);
        tv_month.setTypeface(typeface);
        sunTxt.setTypeface(typeface);
        moonTxt.setTypeface(typeface);

        Typeface typefaceText = Typeface.createFromAsset(getActivity().getAssets(),"fonts/arlrb.TTF");
        sunrise.setTypeface(typefaceText);
        sunset.setTypeface(typefaceText);
        moonrise.setTypeface(typefaceText);
        moonset.setTypeface(typefaceText);

        gridview = (GridView) root.findViewById(R.id.gv_calendar);



        gridViewSet();
        parseXML();



        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(cal_month.get(GregorianCalendar.YEAR) >2020)
                {
                    if(currentMonth == 0 && cal_month.get(GregorianCalendar.YEAR) == 2023)
                    {
                        Toast.makeText(getContext(),"???????????????????????????????????????????????? ???????????? ????????????", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        setPreviousMonth();
                        allFunc();
                    }
                }
            }
        });
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(cal_month.get(GregorianCalendar.YEAR) <2024)
                {
                    if(currentMonth == 11 && cal_month.get(GregorianCalendar.YEAR) == 2023)
                    {
                        Toast.makeText(getContext(),"???????????????????????????????????????????????? ???????????? ????????????", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        setNextMonth();
                        allFunc();

                    }
                }
            }
        });



        return root;
    }
//    private void refreshAd() {
//        AdLoader.Builder builder = new AdLoader.Builder(activity, getString(R.string.admob_unit_banner_id));
//
//        builder.forUnifiedNativeAd(
//                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//                    // OnUnifiedNativeAdLoadedListener implementation.
//                    @Override
//                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                        // If this callback occurs after the activity is destroyed, you must call
//                        // destroy and return or you may get a memory leak.
//                        boolean isDestroyed = false;
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                            isDestroyed =  activity.isDestroyed();
//                        }
//                        if (isDestroyed || activity.isFinishing() ||  activity.isChangingConfigurations()) {
//                            unifiedNativeAd.destroy();
//                            return;
//                        }
//                        // You must call destroy on old ads when you are done with them,
//                        // otherwise you will have a memory leak.
//                        if (nativeAd != null) {
//                            nativeAd.destroy();
//                        }
//                        nativeAd = unifiedNativeAd;
//                        FrameLayout frameLayout = activity.findViewById(R.id.fl_adplaceholder);
//                        UnifiedNativeAdView adView =
//                                (UnifiedNativeAdView) getLayoutInflater()
//                                        .inflate(R.layout.ad_unified, null);
//                        populateUnifiedNativeAdView(unifiedNativeAd, adView);
//                        frameLayout.removeAllViews();
//                        frameLayout.addView(adView);
//                    }
//                });
//
//        VideoOptions videoOptions =
//                new VideoOptions.Builder().setStartMuted(true).build();
//
//        NativeAdOptions adOptions =
//                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);
//
//        AdLoader adLoader =
//                builder
//                        .withAdListener(
//                                new AdListener() {
//                                    @Override
//                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                                        String error =
//                                                String.format(
//                                                        "domain: %s, code: %d, message: %s",
//                                                        loadAdError.getDomain(),
//                                                        loadAdError.getCode(),
//                                                        loadAdError.getMessage());
//                                        Toast.makeText(
//                                                        activity,
//                                                        "Failed to load native ad with error " + error,
//                                                        Toast.LENGTH_SHORT)
//                                                .show();
//                                    }
//                                })
//                        .build();
//
//        adLoader.loadAd(new AdRequest.Builder().build());
//
//    }
//    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
//        // Set the media view.
//        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
//
//        // Set other ad assets.
//        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//        adView.setBodyView(adView.findViewById(R.id.ad_body));
//        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
//        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));
//        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
//
//        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
//        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
//        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
//
//        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
//        // check before trying to display them.
//        if (nativeAd.getBody() == null) {
//            adView.getBodyView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getBodyView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//        }
//
//        if (nativeAd.getCallToAction() == null) {
//            adView.getCallToActionView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getCallToActionView().setVisibility(View.VISIBLE);
//            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//        }
//
//        if (nativeAd.getIcon() == null) {
//            adView.getIconView().setVisibility(View.GONE);
//        } else {
//            ((ImageView) adView.getIconView()).setImageDrawable(
//                    nativeAd.getIcon().getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }
//
//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView())
//                    .setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }
//
//        // This method tells the Google Mobile Ads SDK that you have finished populating your
//        // native ad view with this native ad.
//        adView.setNativeAd(nativeAd);
//
//        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
//        // have a video asset.
//        VideoController vc = nativeAd.getVideoController();
//
//        // Updates the UI to say whether or not this ad has a video asset.
//        if (vc.hasVideoContent()) {
////            videoStatus.setText(String.format(Locale.getDefault(),
////                    "Video status: Ad contains a %.2f:1 video asset.",
////                    vc.getAspectRatio()));
//
//            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
//            // VideoController will call methods on this object when events occur in the video
//            // lifecycle.
//            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
//                @Override
//                public void onVideoEnd() {
//                    // Publishers should allow native ads to complete video playback before
//                    // refreshing or replacing them with another ad in the same UI location.
////                    refresh.setEnabled(true);
////                    videoStatus.setText("Video status: Video playback has ended.");
//                    super.onVideoEnd();
//                }
//            });
//        } else {
////            videoStatus.setText("Video status: Ad does not contain a video asset.");
////            refresh.setEnabled(true);
//        }
//    }


    private void panchangamApi(String date)
    {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        JSONObject object2 = jsonArray.getJSONObject(0);
                        JSONArray jsonArray1 = object2.getJSONArray(Constant.TAB);

                        sunrise.setText(jsonArray.getJSONObject(0).getString(Constant.SUNRISE));
                        sunset.setText(jsonArray.getJSONObject(0).getString(Constant.SUNSET));
                        moonrise.setText(jsonArray.getJSONObject(0).getString(Constant.MOONRISE));
                        moonset.setText(jsonArray.getJSONObject(0).getString(Constant.MOONSET));
                        Gson g = new Gson();
                        ArrayList<PanchangamTab> panchangamTabs = new ArrayList<>();

                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            if (jsonObject1 != null) {
                                PanchangamTab group = g.fromJson(jsonObject1.toString(), PanchangamTab.class);
                                panchangamTabs.add(group);
                            } else {
                                break;
                            }
                        }
                        panchangamTabAdapter = new PanchangamTabAdapter(activity, panchangamTabs);
                        recyclerView.setAdapter(panchangamTabAdapter);

                    }
                    else {
                        sunrise.setText("-");
                        sunset.setText("-");
                        moonrise.setText("-");
                        moonset.setText("-");
                        ArrayList<PanchangamTab> panchangamTabs = new ArrayList<>();

                        panchangamTabAdapter = new PanchangamTabAdapter(activity, panchangamTabs);
                        recyclerView.setAdapter(panchangamTabAdapter);


                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PANCHANGAM_URL, params,true);

    }

    private void allFunc()
    {
        refreshCalendar();
        gridViewSet();
        clickedDate = 0;
        selectedGridDate = HwAdapter.day_string.get(HwAdapter.firstDay -1);
        if(cal_month.get(GregorianCalendar.YEAR) == 2021)
        {
            dateFormat = 1+" - "+month[currentMonth]+" - "+cal_month.get(GregorianCalendar.YEAR)+" - ????????????????????????????????? ????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth <3)
        {
            dateFormat = 1+" - "+month[currentMonth]+" - "+cal_month.get(GregorianCalendar.YEAR)+" - ????????????????????????????????? ????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth >2)
        {
            dateFormat = 1+" - "+month[currentMonth]+" - "+cal_month.get(GregorianCalendar.YEAR)+" -  ?????????????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth <3)
        {
            dateFormat = 1+" - "+month[currentMonth]+" - "+cal_month.get(GregorianCalendar.YEAR)+" -  ?????????????????????" ;

        }else if(cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth >2 )
        {
            dateFormat = 1+" - "+month[currentMonth]+" - "+cal_month.get(GregorianCalendar.YEAR)+" -  ?????????????????????" ;
        }
        hwAdapter.notifyDataSetChanged();
        loadXmlFile = monthE[currentMonth]+"_"+cal_month.get(GregorianCalendar.YEAR);
        parseXML();
    }

    private void gridViewSet()
    {
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                selectedGridDate = HwAdapter.day_string.get(position);
                String[] separatedTime = HwAdapter.day_string.get(position).split("-");

                String cl_day = separatedTime[2].replaceFirst("^0*", "");
                String cl_month = separatedTime[1].replaceFirst("^0*", "");
                String cl_year = separatedTime[0].replaceFirst("^0*", "");

                clickedDate = (Integer.parseInt(cl_day)-1);


                if(cal_month.get(GregorianCalendar.YEAR) == 2021)
                {
                    dateFormat = cl_day+" - "+month[(Integer.parseInt(cl_month)-1)]+" - "+cl_year+" -  ????????????" ;

                }else if(cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth <3)
                {
                    dateFormat = cl_day+" - "+month[(Integer.parseInt(cl_month)-1)]+" - "+cl_year+" -  ????????????" ;

                }else if(cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth >3)
                {
                    dateFormat = cl_day+" - "+month[(Integer.parseInt(cl_month)-1)]+" - "+cl_year+" -  ?????????????????????" ;

                }else if(cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth <3 && clickedDate<21)
                {
                    dateFormat = cl_day+" - "+month[(Integer.parseInt(cl_month)-1)]+" - "+cl_year+" -  ?????????????????????" ;

                }else if(cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth >1 && clickedDate>20)
                {
                    dateFormat = cl_day+" - "+month[(Integer.parseInt(cl_month)-1)]+" - "+cl_year+" -  ?????????????????????" ;
                }
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, getActivity());
                hwAdapter.notifyDataSetChanged();
                if(loadXmlFile.equals(monthE[(Integer.parseInt(cl_month)-1)]+"_"+ Integer.parseInt(cl_year)))
                {
                    try {
                        printRecords();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else
                {
                    loadXmlFile = monthE[(Integer.parseInt(cl_month)-1)]+"_"+ Integer.parseInt(cl_year);
                    parseXML();
                }
            }
        });
    }

    protected void setNextMonth()
    {
        currentMonth++;
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH))
        {
            currentMonth = 0;
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);

        }else
        {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) + 1);
        }
        tv_month.setText(month[currentMonth]+" - "+(cal_month.get(GregorianCalendar.YEAR)));

    }

    protected void setPreviousMonth()
    {
        currentMonth--;
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH))
        {
            currentMonth =11;
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);

        }else
        {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
        tv_month.setText(month[currentMonth]+" - "+(cal_month.get(GregorianCalendar.YEAR)));
    }

    public void refreshCalendar()
    {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
    }
    private void parseXML()
    {
        XmlPullParserFactory parserFactory;

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = parserFactory.newPullParser();
            try
            {
                InputStream inputStream = getActivity().getAssets().open("Months/"+loadXmlFile+".xml");
                pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                pullParser.setInput(inputStream,null);
                processParser(pullParser);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void processParser(XmlPullParser pullParser) throws IOException, XmlPullParserException, ParseException {
        records = new ArrayList<>();
        int eventType = pullParser.getEventType();

        XmlRecords currentRecord = null;

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            String eltName = null;
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                    eltName = pullParser.getName();

                    if("record".equals(eltName))
                    {
                        currentRecord = new XmlRecords();
                        records.add(currentRecord);

                    }else if(currentRecord != null)
                    {
                        if("date".equals(eltName))
                        {
                            currentRecord.Date = pullParser.nextText();

                        }else if("sunrise".equals(eltName))
                        {
                            currentRecord.Sunrise = pullParser.nextText();

                        }else if("sunset".equals(eltName))
                        {
                            currentRecord.Sunset = pullParser.nextText();

                        }else if("moonrise".equals(eltName))
                        {
                            currentRecord.Moonrise = pullParser.nextText();

                        }else if("moonset".equals(eltName))
                        {
                            currentRecord.Moonset = pullParser.nextText();

                        }else if("rutuvu".equals(eltName))
                        {
                            currentRecord.Ruthu = pullParser.nextText();

                        }else if("masam".equals(eltName))
                        {
                            currentRecord.Masam = pullParser.nextText();

                        }else if("paksham".equals(eltName))
                        {
                            currentRecord.Paksham = pullParser.nextText();

                        }else if("kalam".equals(eltName))
                        {
                            currentRecord.Kalam = pullParser.nextText();

                        }else if("thidi".equals(eltName))
                        {
                            currentRecord.Thidi = pullParser.nextText();

                        }else if("vaara".equals(eltName))
                        {
                            currentRecord.Week = pullParser.nextText();

                        }else if("nakshatra".equals(eltName))
                        {
                            currentRecord.Nakshatram = pullParser.nextText();

                        }else if("yogam".equals(eltName))
                        {
                            currentRecord.Yogam = pullParser.nextText();

                        }else if("karana".equals(eltName))
                        {
                            currentRecord.Karanam = pullParser.nextText();

                        }else if("rahu".equals(eltName))
                        {
                            currentRecord.Rahuv = pullParser.nextText();

                        }else if("yamag".equals(eltName))
                        {
                            currentRecord.Yama = pullParser.nextText();

                        }else if("varjyam".equals(eltName))
                        {
                            currentRecord.Varjyam = pullParser.nextText();

                        }else if("gulika".equals(eltName))
                        {
                            currentRecord.Gulika = pullParser.nextText();

                        }else if("dhurmuhu".equals(eltName))
                        {
                            currentRecord.Dhurmuhurth = pullParser.nextText();

                        }else if("festival".equals(eltName))
                        {
                            currentRecord.Festival = pullParser.nextText();

                        }
                    }
                    break;
            }
            eventType = pullParser.next();
        }
        printRecords();
    }

    private void printRecords() throws ParseException {
        String date  = (clickedDate + 1) + "" ;
        String strCurrentDate = loadXmlFile + "_"+date;
        SimpleDateFormat format = new SimpleDateFormat("MMMM_yyyy_dd");
        Date newDate = null;

            newDate = format.parse(strCurrentDate);


        format = new SimpleDateFormat("yyyy-MM-dd");
        String cadate = format.format(newDate);
        Log.d("CURRENTDATE",""+cadate);
        //panchangamApi(cadate);
        panchangamList(cadate);

        String festival;

        if(records.get(clickedDate).Festival == null)
        {
            festival = "";
        }else
        {
            festival = records.get(clickedDate).Festival;
        }
        String htmlData;

        if(!changed)
        {
            changed = true;
            htmlData = "<html><style type='text/css'>@font-face { font-family: sree; src: url('fonts/sree.ttf'); } body p {font-family: sree;}</style><head><meta name='viewport' user-scalable=no' /></head><body align='center' style='padding: 0' >" +
                    "<div style='color:#0F1970;text-align:center;font-size:19;font-family: sree;'>"+  dateFormat + "</div>" +

                    "<div style='font-weight:bold;text-align:left;margin-left:10px;line-height:1.5;font-size:15;font-family: sree;'>" +

                    "<span style='color:#006600'>"+
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" +": &nbsp;"+ records.get(clickedDate).Thidi + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "??????????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Week + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????????????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Nakshatram + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Yogam + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Karanam + "</div>"+"</span>" +"<br>" +
                    "<span style='color:#d31d8c'>"+
                    "<div style='width:26%;text-align:left;float:left'>" + "????????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" +":&nbsp;"+ records.get(clickedDate).Rahuv + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "?????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Yama + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "?????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Varjyam + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "?????????????????? " + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Gulika + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "????????????????????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Dhurmuhurth + "</div>"+"</span>" +"<br>" +

                    "<div style='color:#0F1970;font-weight:bold;margin-top:7px;text-align:center;font-size:17;font-family: sree;'>" +
                    "* &nbsp; " +festival +"&nbsp; *" +"<br>"+ "</div>" +
                    "</body></html>";

        }else
        {
            changed = false;
            htmlData = "<html><style type='text/css'>@font-face { font-family: sree; src: url('fonts/sree.ttf'); } body p {font-family: sree;}</style><head><meta name='viewport' user-scalable=no' /></head><body align='center' style='padding: 0' >" +
                    "<div style='color:#0F1970;text-align:center;font-size:19;font-family: sree;'>"+  dateFormat+ "</div>" +

                    "<div style='font-weight:bold;text-align:left;margin-left:10px;line-height:1.5;font-size:15;font-family: sree;'>" +
                    "<span style='color:#006600'>"+
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" +": &nbsp;"+ records.get(clickedDate).Thidi + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "??????????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Week + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "????????????????????????" + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Nakshatram + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Yogam + "</div>" +"<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "???????????? " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;"+records.get(clickedDate).Karanam + "</div>"+"</span>" +"<br>" +
                    "<span style='color:#d31d8c'>"+
                    "<div style='width:26%;text-align:left;float:left'>" + "????????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" +":&nbsp;"+ records.get(clickedDate).Rahuv + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "?????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Yama + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "?????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Varjyam + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "?????????????????? " + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Gulika + "</div>" +"<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "????????????????????????????????????" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;"+records.get(clickedDate).Dhurmuhurth + "</div>"+"</span>" +"<br>" +

                    "<div style='color:#0F1970;font-weight:bold;margin-top:7px;text-align:center;font-size:17;font-family: sree;'>" +
                    "* &nbsp;" +festival +"&nbsp; *" +"<br>"+ "</div>" +

                    "</body></html>";
        }
    }

    private void panchangamList(String cadate)
    {
        if (databaseHelper.getmodelPanchangamList(cadate).size() !=0){
            recyclerView.setVisibility(View.VISIBLE);
            sunrise.setText(databaseHelper.getmodelPanchangamList(cadate).get(0).getSunrise());
            sunset.setText(databaseHelper.getmodelPanchangamList(cadate).get(0).getSunset());
            moonrise.setText(databaseHelper.getmodelPanchangamList(cadate).get(0).getMoonrise());
            moonset.setText(databaseHelper.getmodelPanchangamList(cadate).get(0).getMoonset());

            if (databaseHelper.getmodelPanchangamTabList(databaseHelper.getmodelPanchangamList(cadate).get(0).getId()).size() !=0){
                panchangamTabAdapter = new PanchangamTabAdapter(activity, databaseHelper.getmodelPanchangamTabList(databaseHelper.getmodelPanchangamList(cadate).get(0).getId()));
                recyclerView.setAdapter(panchangamTabAdapter);

            }
            else {
                recyclerView.setVisibility(View.GONE);
            }


        }
        else {
            recyclerView.setVisibility(View.GONE);
            sunrise.setText("-");
            sunset.setText("-");
            moonrise.setText("-");
            moonset.setText("-");

        }


    }
}

