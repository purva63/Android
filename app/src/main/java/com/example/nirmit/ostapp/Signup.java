package com.example.nirmit.ostapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Signup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    String em,pa,name2,con;
    Button next,reg,logout;
    EditText email,pass,contact,name,address,schedule;
    TextView add,sc;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase,mD1;
    RadioGroup category;
    RadioButton button;
    View v;
    String cat="Artist";
    public static final String TAG="SignInActivity";
    FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.fragment_signup, container, false);
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email=(EditText)v.findViewById(R.id.email);
        name=(EditText)v.findViewById(R.id.name);
        contact=(EditText)v.findViewById(R.id.contact);
        add=(TextView)v.findViewById(R.id.add);
        sc=(TextView)v.findViewById(R.id.sc);
        address=(EditText)v.findViewById(R.id.address);
        schedule=(EditText)v.findViewById(R.id.schedule);
        pass=(EditText)v.findViewById(R.id.pass);
        next=(Button)v.findViewById(R.id.next);
        reg=(Button)v.findViewById(R.id.reg);
        logout=(Button)v.findViewById(R.id.logout);
        mAuthStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    next.setEnabled(true);
                    next.setBackgroundColor(Color.rgb(110,191,245));
                    logout.setEnabled(true);
                    logout.setBackgroundColor(Color.rgb(118,245,122));
                }

            }
        };
        category=(RadioGroup)v.findViewById(R.id.category);
        next.setEnabled(false);
        logout.setEnabled(false);
        add.setVisibility(View.GONE);
        sc.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        schedule.setVisibility(View.GONE);
        category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int c=category.getCheckedRadioButtonId();
                button=(RadioButton)v.findViewById(c);
                 cat=button.getText().toString();
                if(!(cat.equals("Artist")))
                {
                    add.setVisibility(View.VISIBLE);
                    sc.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    schedule.setVisibility(View.VISIBLE);
                }
               else
                {
                    add.setVisibility(View.GONE);
                    sc.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    schedule.setVisibility(View.GONE);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in2 = new Intent("page2");
                startActivity(in2);
            }

        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REG();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                next.setEnabled(false);
                logout.setEnabled(false);
                next.setBackgroundColor(Color.rgb(168,186,168));
                logout.setBackgroundColor(Color.rgb(168,186,168));
            }
        });
        return v;
    }
    public void REG() {
         em = email.getText().toString();
         pa = pass.getText().toString();
          name2 =name.getText().toString();
          con = contact.getText().toString();
        if (em.equals("") || pa.equals(""))
            Toast.makeText(getActivity(), "password or email not set", Toast.LENGTH_LONG).show();
        else {
            mAuth.createUserWithEmailAndPassword(em, pa)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "registered", Toast.LENGTH_LONG).show();
                                String y=email.getText().toString();
                                for(int i=0;i<y.length();i++)
                                    y=y.replace(".","");
                                y=y.substring(0,y.indexOf("@"));
                                if(cat.equals("Artist")) {
                                    Artists artists = new Artists(em, pa, name2, con,"Artist");
                                    mDatabase.child(y).setValue(artists);
                                }
                                else{
                                    String addr=address.getText().toString();
                                    String sch=schedule.getText().toString();
                                    Manager manager;
                                    if(cat.equals("Manager"))
                                     manager = new Manager(em, pa, name2, con,"Manager",addr,sch);
                                    else
                                        manager = new Manager(em, pa, name2, con,"Restaurant",addr,sch);
                                    mDatabase.child(y).setValue(manager);
                                    address.setText("");
                                    schedule.setText("");
                                }
                                Toast.makeText(getContext(),"profile entered",Toast.LENGTH_LONG).show();
                                email.setText("");
                                pass.setText("");
                                contact.setText("");
                                name.setText("");
                                next.setEnabled(true);
                                next.setBackgroundColor(Color.rgb(110,191,245));
                                logout.setEnabled(true);
                                logout.setBackgroundColor(Color.rgb(118,245,122));
                            } else {
                                Toast.makeText(getContext(), "NOT registered", Toast.LENGTH_LONG).show();
                                email.setText("");
                                pass.setText("");
                            }
                        }
                    });
        }
    }
   /* @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }*/
   public void onStart()
   {
       super.onStart();
       mAuth.addAuthStateListener(mAuthStateListener);
   }
    public void onStop() {
        super.onStop();
        if(mAuthStateListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
    @Override
    public void onPause(){
        super.onPause();

    }
    @Override
    public void onResume(){
        super.onResume();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
