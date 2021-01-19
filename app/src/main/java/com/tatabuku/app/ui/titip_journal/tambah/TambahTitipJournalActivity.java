package com.tatabuku.app.ui.titip_journal.tambah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tatabuku.app.databinding.ActivityTambahTitipJournalBinding;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.util.ImageHelper;
import com.tatabuku.app.util.StringHelper;

import java.io.File;
import java.util.Calendar;


public class TambahTitipJournalActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityTambahTitipJournalBinding binding;
    private DatePickerDialog datePicker;
    private TambahTitipJurnalViewModel viewModel;

    private String currentNominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureViewModel();
        configureView();
        setupDatePicker();


    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TambahTitipJurnalViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                finish();
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });
    }

    private void setupView(){
        binding = ActivityTambahTitipJournalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog( this,
                TambahTitipJournalActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void configureView(){
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tambahTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        registerForContextMenu(binding.tambahFoto);
        binding.tambahFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.tambahFoto);
            }
        });

        binding.batalkanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tambahJumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentNominal)) {
                    binding.tambahJumlah.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormat(parsed);

                        currentNominal = formatted;
                        binding.tambahJumlah.setText(formatted);
                        binding.tambahJumlah.setSelection(formatted.length());
                    } else {
                        currentNominal = "";
                        binding.tambahJumlah.setText("");
                    }
                    binding.tambahJumlah.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                String date = StringHelper.formatJurnalDate(binding.tambahTanggal.getText().toString());
                String description = binding.inputDeskripsi.getText().toString();
                int jumlah = 0;
                if (binding.tambahJumlah.getText().toString().isEmpty() == false) {
                    jumlah = Integer.valueOf(binding.tambahJumlah.getText().toString().replaceAll("[Rp,.]", "").trim());
                }

                viewModel.createTitipJurnal(date, jumlah, description);
            }
        });

        binding.editButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }));
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 2);
            }
        }
    }

    private void pickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String path = ImageHelper.getUriRealPath(this, selectedImage);
                    File file = new File(path);
                    viewModel.setSelectedFile(file);
                    Glide.with(this).load(selectedImage).apply(RequestOptions.circleCropTransform()).into(binding.tambahFoto);
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = ImageHelper.getImageUri(this, photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(ImageHelper.getUriRealPath(this, tempUri));

                    viewModel.setSelectedFile(finalFile);
                    Glide.with(this).load(photo).apply(RequestOptions.circleCropTransform()).into(binding.tambahFoto);
                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month+1) + "-" + day;
        binding.tambahTanggal.setText(StringHelper.formatDatePicker(date));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v == binding.tambahFoto) {
            menu.setHeaderTitle("Select Image");
            menu.add(9,0,0,"Camera");
            menu.add(9,1,0,"Gallery");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == 9) {
            if (item.getItemId() == 0) {
                openCamera();
            } else {
                pickImage();
            }
        }

        return super.onContextItemSelected(item);
    }
}