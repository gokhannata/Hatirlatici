package com.example.reminderapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Yapilacaklar extends Fragment implements CustomAdapter.OnNoteListener{

    List<Yapilacak> notList = new ArrayList<>();
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;
    Button share;
    DatabaseReference myRef2;
    public static String id2;
    private YapilacakEkle yapilacakEkleFragment;

    String uID;
    FirebaseAuth mAuth;
    MenuItem settings;

    public Yapilacaklar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yapilacak_listesi, container, false);
        mAuth = FirebaseAuth.getInstance();
        myRef2 = FirebaseDatabase.getInstance().getReference().child("Notlar");


        yapilacakEkleFragment=new YapilacakEkle();

        recyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new CustomAdapter(notList,this );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.YapilacakKaydet);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.YapilacakEkle){
            setFragment(yapilacakEkleFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tabLayout,fragment);
        fragmentTransaction.commit();

    }
    private boolean updateNot(String id, String name, String icerik,String tarih,String saat,String notDurumu) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(id2).child("Notlar").child(id);
        Yapilacak artist = new Yapilacak(id, name, icerik,tarih,saat,notDurumu);
        dR.setValue(artist);


        Toast.makeText(getContext(), "Not Güncellendi.", Toast.LENGTH_LONG).show();
        return true;
    }
    /*
    private void showUpdateDeleteDialog( final String notId, String notTitle,String notDesc,String notTarih,String notSaat) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextNotId);
        editTextName.setText(notTitle);

        final   EditText spinnerGenre =  dialogView.findViewById(R.id.editTextNotDesc);
        spinnerGenre.setText(notDesc);
        final TextView textViewtarihUpdate = dialogView.findViewById(R.id.textViewTarihUpdate);
        textViewtarihUpdate.setText(notTarih);
        final TextView textViewSaatUpdate=dialogView.findViewById(R.id.textViewSaatUpdate);
        textViewSaatUpdate.setText(notSaat);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateNot);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteNot);
        final Button buttonTarih  = dialogView.findViewById(R.id.buttonTarihUpdate);
        final Button buttonSaat   = dialogView.findViewById(R.id.buttonSaatUpdate);
        final Button buttonShare  = dialogView.findViewById(R.id.buttonShare);

        buttonTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewtarihUpdate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, day,month,year);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        dialogBuilder.setTitle("Güncelleme-Silme Ekranı");
        final  AlertDialog b = dialogBuilder.create();
        b.show();

        buttonSaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // hourOfDay ve minute değerleri seçilen saat değerleridir.
                                // Edittextte bu değerleri gösteriyoruz.
                                textViewSaatUpdate.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                timePickerDialog.show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getText().toString().trim();
                String tarih = textViewtarihUpdate.getText().toString().trim();
                String saat=textViewSaatUpdate.getText().toString().trim();
                String notDurumu ;
                CheckBox checkBox = dialogView.findViewById(R.id.checkBox);
                if(checkBox.isChecked())  notDurumu = "1"; else notDurumu = "0";

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(genre)) {
                    updateNot(notId, name, genre,tarih,saat,notDurumu);
                    b.dismiss();
                }
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextName.getText().toString();
                String description=spinnerGenre.getText().toString();
                String message=description.concat(" \n Reminder App ile gönderildi.");
                Intent sharingIntent=new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,name);
                sharingIntent.putExtra(Intent.EXTRA_TEXT,message);

                startActivity(Intent.createChooser(sharingIntent,"Share Using"));
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteNot(notId);
                b.dismiss();
            }
        });
    }
    private boolean deleteNot(String id) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(id2);
        Query applesQuery = ref.child("Notlar").child(id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
                Toast.makeText(getContext(), "Not silindi.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Hata!.\n"+databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return true;
    }

     */
    @Override
    public void onStart() {
        super.onStart();

        //attaching value event listener
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notList.clear();
                //  Toast.makeText(AsiListele.this, "OndataChange", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Yapilacak not1 = ds.getValue(Yapilacak.class);
                        if (not1 != null) {
                            notList.add(not1);
                            ds.getKey();
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
        mAdapter = new CustomAdapter(notList, (CustomAdapter.OnNoteListener) this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Yapilacak not = notList.get(position);
        //showUpdateDeleteDialog(not.getId(),not.getYapilacak(),not.getEtiket(),not.getTarih(),not.getSaat());

    }
}
