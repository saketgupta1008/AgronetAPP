package com.hackfest2018.tabs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.

     */

    private static final String MODEL_FILE = "file:///android_asset/frozen_model.pb";
    private static final String LABEL_FILENAME = "file:///android_asset/lables.txt";
    private static final String INPUT_NODE = "X";
    private static final String OUTPUT_NODE = "output:0";
    private static final String[] OUTPUT_NODES={"output:0"};
    public static TextView resultt;
    public static final int OUTPUT_SIZE=1;
    public static int[] resu=new int[2];
    public static double[] input_nodes=new double[8];
    public static TensorFlowInferenceInterface inferenceInterface;
    private static final List<String> labels = new ArrayList<String>();
    private List<String> displayedLabels = new ArrayList<>();
    private static final String LOG_TAG ="HI";

    // * The {@link ViewPager} that will host the section contents.

    AssetManager assetManager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultt=(TextView)findViewById(R.id.display);
        String actualFilename = LABEL_FILENAME.split("file:///android_asset/")[1];
        Log.i(LOG_TAG, "Reading labels from: " + actualFilename);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(actualFilename)));
            String line;
            while ((line = br.readLine()) != null) {
                labels.add(line);
                if (line.charAt(0) != '_') {
                    displayedLabels.add(line.substring(0, 1).toUpperCase() + line.substring(1));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("Problem reading label file!", e);
        }
        inferenceInterface=new TensorFlowInferenceInterface(getAssets(),MODEL_FILE);

     /*   Button sub=(Button)findViewById(R.id.out) ;
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        resultt=(TextView)findViewById(R.id.display);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            final int i=getArguments().getInt(ARG_SECTION_NUMBER);
            final View[] rootView = {null};
            if(i==1) {
                 rootView[0] = inflater.inflate(R.layout.fragment_main, container, false);
             //   TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                final EditText name1 = (EditText) rootView[0].findViewById(R.id.q1_name);
                final EditText email1 = (EditText) rootView[0].findViewById(R.id.q2_mail);
                final EditText password1 = (EditText) rootView[0].findViewById(R.id.q3_password);
                Button b=(Button) rootView[0].findViewById(R.id.submit1);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        input_nodes[0]=Double.parseDouble(name1.getText().toString());
                        input_nodes[1]=Double.parseDouble(email1.getText().toString());
                        input_nodes[2]=Double.parseDouble(password1.getText().toString());
                    }
                });
            }
            else  if(i==2) {
                 rootView[0] = inflater.inflate(R.layout.frag2, container, false);
       //         TextView textView = (TextView) rootView.findViewById(R.id.section_label1);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                final EditText name2 = (EditText) rootView[0].findViewById(R.id.q4_name);
                final EditText email2 = (EditText) rootView[0].findViewById(R.id.q5_mail);
                final EditText password2 = (EditText) rootView[0].findViewById(R.id.q6_password);
                Button b=(Button) rootView[0].findViewById(R.id.submit2);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        input_nodes[3]=Double.parseDouble(name2.getText().toString());
                        input_nodes[4]=Double.parseDouble(email2.getText().toString());
                        input_nodes[5]=Double.parseDouble(password2.getText().toString());
                    }
                });
            }
//            else  if(i==3) {
//                 rootView[0] = inflater.inflate(R.layout.frag3, container, false);
//             //   TextView textView = (TextView) rootView.findViewById(R.id.section_label2);
//                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//                final EditText name3 = (EditText) rootView[0].findViewById(R.id.q7_name);
//                final EditText email3 = (EditText) rootView[0].findViewById(R.id.q8_mail);
//                final EditText password3 = (EditText) rootView[0].findViewById(R.id.q9_password);
//                Button b=(Button) rootView[0].findViewById(R.id.submit3);
//                b.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        input_nodes[6]=Double.parseDouble(name3.getText().toString());
//                        input_nodes[7]=Double.parseDouble(email3.getText().toString());
//                        input_nodes[8]=Double.parseDouble(password3.getText().toString());
//                    }
//                });
//            }
            else if(i==3){
                rootView[0] = inflater.inflate(R.layout.frag4, container, false);
                //   TextView textView = (TextView) rootView.findViewById(R.id.section_label2);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                final EditText name4 = (EditText) rootView[0].findViewById(R.id.q10_name);
                final EditText email4 = (EditText) rootView[0].findViewById(R.id.q11_mail);
                //final EditText password4 = (EditText) rootView[0].findViewById(R.id.q12_password);
                Button b=(Button) rootView[0].findViewById(R.id.submit4);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        input_nodes[6]=Double.parseDouble(name4.getText().toString());
                        input_nodes[7]=Double.parseDouble(email4.getText().toString());

                        for (String member : labels){
                            Log.i("Member name: ", member);
                        }



                        inferenceInterface.feed(INPUT_NODE,input_nodes,1,8);
                        inferenceInterface.run(OUTPUT_NODES);
                        String abc="";
                        inferenceInterface.fetch("output:0",resu);

                        //Toast.makeText(getApplicationContext(), "Your toast message.",
                        //        Toast.LENGTH_SHORT).show();
                        RelativeLayout rl=(RelativeLayout)rootView[0].findViewById(R.id.rl);
                        rl.setBackground(getContext().getDrawable(R.drawable.finall));

                        name4.setVisibility(View.INVISIBLE);
                        email4.setVisibility(View.INVISIBLE);
                     //   password4.setVisibility(View.INVISIBLE);
                        b.setVisibility(View.INVISIBLE);
                        TextView text3 = (TextView)rootView[0].findViewById(R.id.textView7);
                        ImageView im1=(ImageView)rootView[0].findViewById(R.id.imageView5);
                        ImageView im2=(ImageView)rootView[0].findViewById(R.id.imageView6);
                        //ImageView im3=(ImageView)rootView[0].findViewById(R.id.imageView7);
                        im1.setVisibility(View.INVISIBLE);
                        im2.setVisibility(View.INVISIBLE);

                     //   text3.setVisibility(View.VISIBLE);3
                        TextView res = (TextView)rootView[0].findViewById(R.id.resulttt);

                        res.setText(labels.get(resu[0]));


                        //resultt=(TextView)findViewById(R.id.display);
                        //resultt.setText(labels.get(resu[0]));
                        //resultt.setText("HELLO");
                       // rootView[0] = inflater.inflate(R.layout.result, container, false);
                        //setContentView(R.layout.result);
                    }
                });

            }
            return rootView[0];
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
