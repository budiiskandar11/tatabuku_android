package com.tatabuku.app.ui.fix_asset.tambah_asset;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tatabuku.app.databinding.ActivityTambahAssetBinding;
import com.tatabuku.app.model.fixed_asset.GetPurchasesModel;
import com.tatabuku.app.model.fixed_asset.GetPurchasesOrderLine;
import com.tatabuku.app.model.fixed_asset.GetSupplierModel;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.util.ImageHelper;
import com.tatabuku.app.util.StringHelper;

import java.io.File;
import java.util.Calendar;

public class TambahAssetActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    public final static String TAMBAH_ASSET_CATEG_ID = "TAMBAH_ASSET_CATEG_ID";
    public final static String TAMBAH_ASSET_CATEG_NAME = "TAMBAH_ASSET_CATEG_NAME";

    private ActivityTambahAssetBinding binding;
    private DatePickerDialog datePicker;
    private TambahAssetViewModel viewModel;
    private String currentNominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        setupDatePicker();
        configureView();
        configureIntent();
        viewModel.fetchSupplier("");
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TambahAssetViewModel.class);
    }

    private void configureViewModel() {

        viewModel.getSelectedSupplier().observe(this, new Observer<GetSupplierModel>() {
            @Override
            public void onChanged(GetSupplierModel supplier) {
                if (supplier != null) {
                    viewModel.fetchPurchases(supplier.getId());
                    binding.searchSupplier.setText(supplier.getName());
                } else {
                    binding.searchSupplier.setText("");
                }
                viewModel.getSelectedPurchase().setValue(null);
            }
        });

        viewModel.getSelectedPurchase().observe(this, new Observer<GetPurchasesModel>() {
            @Override
            public void onChanged(GetPurchasesModel purchase) {
                if (purchase != null) {
                    viewModel.getPoLines().setValue(purchase.getOrderLine());
                    binding.pilihPo.setText(purchase.getName());
                } else {
                    binding.pilihPo.setText("");
                }
                viewModel.getSelectedPOLines().setValue(null);
            }
        });

        viewModel.getSelectedPOLines().observe(this, new Observer<GetPurchasesOrderLine>() {
            @Override
            public void onChanged(GetPurchasesOrderLine poLine) {
                if (poLine != null) {
                    binding.pilihProduct.setText(poLine.getName());
                    Double harga = poLine.getHarga();
                    binding.nilaiDetailAsset.setText(String.valueOf(harga.intValue()));
                } else {
                    binding.pilihProduct.setText("");
                }
            }
        });

        viewModel.getIsNewPurchase().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNewPurchase) {
                InputMethodManager imm = (InputMethodManager) TambahAssetActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.searchSupplier.getWindowToken(), 0);

                if (isNewPurchase) {
                    binding.nilaiDetailAsset.setFocusable(false);
                    binding.nilaiDetailAsset.setFocusableInTouchMode(false);
                    binding.overlayNilaiAsset.setVisibility(View.VISIBLE);
                } else {
                    binding.nilaiDetailAsset.setFocusable(true);
                    binding.nilaiDetailAsset.setFocusableInTouchMode(true);
                    binding.overlayNilaiAsset.setVisibility(View.GONE);
                }
            }
        });

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

    private void setupView() {
        binding = ActivityTambahAssetBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.nomorDetailAsset.setText("-");
        binding.nomorDetailAsset.setFocusable(false);
        binding.nomorDetailAsset.setClickable(false);
        binding.overlayNomorAsset.setVisibility(View.VISIBLE);

        binding.usiaDepresiasi.setText("");
        binding.usiaDepresiasi.setFocusableInTouchMode(false);
        binding.usiaDepresiasi.setFocusable(false);
        binding.overlayUsiaDepresiasi.setVisibility(View.VISIBLE);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this,
                TambahAssetActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void configureView() {

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerForContextMenu(binding.searchSupplier);
//        binding.searchSupplier.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String supplier_name = binding.searchSupplier.getText().toString();
//                GetSupplierModel supplier = viewModel.getSelectedSupplier().getValue();
//
//                if (binding.searchSupplier.getText().toString().equals("")) {
//                    if (supplier != null) {
//                        viewModel.clearValue();
//                    }
//                } else if (supplier == null || supplier.getName().equals(binding.searchSupplier.getText().toString()) == false) {
//                    viewModel.fetchSupplier(supplier_name);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        binding.searchSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.searchSupplier);
            }
        });

        registerForContextMenu(binding.pilihPo);
        binding.pilihPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedSupplier().getValue() == null) {
                    showAlert("Perhatian", "Pilih Supplier");
                } else if (viewModel.getPurchases().getValue().size() == 0) {
                    showAlert("Perhatian", "Data kosong");
                } else {
                    openContextMenu(binding.pilihPo);
                }
            }
        });

        registerForContextMenu(binding.pilihProduct);
        binding.pilihProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedPurchase().getValue() == null) {
                    showAlert("Perhatian", "Pilih PO");
                } else if (viewModel.getPoLines().getValue().size() == 0) {
                    showAlert("Perhatian", "Data kosong");
                } else {
                    openContextMenu(binding.pilihProduct);
                }
            }
        });

        binding.nilaiDetailAsset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentNominal)) {
                    binding.nilaiDetailAsset.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormat(parsed);

                        currentNominal = formatted;
                        binding.nilaiDetailAsset.setText(formatted);
                        binding.nilaiDetailAsset.setSelection(formatted.length());
                    } else {
                        currentNominal = "";
                        binding.nilaiDetailAsset.setText("");
                    }
                    binding.nilaiDetailAsset.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                double total = 0;
                if (!binding.nilaiDetailAsset.getText().toString().equals("")) {
                    total = Double.valueOf(binding.nilaiDetailAsset.getText().toString().replaceAll("[Rp,.]","").trim());
                }

                binding.nilaiTotalAsset.setText(StringHelper.numberFormatWithDecimal(total));
            }
        });

        binding.tanggalTambahAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        registerForContextMenu(binding.fotoDetailAsset);
        binding.fotoDetailAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.fotoDetailAsset);
            }
        });

        binding.batalkanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.radioPembelianBaru.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.radioSudahAda.setChecked(!b);
                binding.usiaDepresiasi.setFocusableInTouchMode(false);
                binding.usiaDepresiasi.setFocusable(false);
                binding.overlayUsiaDepresiasi.setVisibility(View.VISIBLE);
                viewModel.getIsNewPurchase().setValue(true);
            }
        });

        binding.radioSudahAda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.radioPembelianBaru.setChecked(!b);
                binding.usiaDepresiasi.setFocusableInTouchMode(true);
                binding.usiaDepresiasi.setFocusable(true);
                binding.overlayUsiaDepresiasi.setVisibility(View.GONE);
                viewModel.getIsNewPurchase().setValue(false);
            }
        });

        binding.terimaBarangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                InputMethodManager imm = (InputMethodManager) TambahAssetActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.searchSupplier.getWindowToken(), 0);
                String date = StringHelper.formatBackendDate(binding.tanggalTambahAsset.getText().toString());
                String name = binding.namaDetailAsset.getText().toString();

                if (binding.nilaiDetailAsset.getText().toString().equals("")) {
                    viewModel.getOnError().setValue("Data tidak lengkap");
                    return;
                }
                int price = Integer.valueOf(binding.nilaiDetailAsset.getText().toString().replaceAll("[Rp,.]", "").trim());

                if (viewModel.getIsNewPurchase().getValue()) {
                    if (binding.quantity.getText().toString().equals("")) {
                        viewModel.getOnError().setValue("Data tidak lengkap");
                        return;
                    }
                    int quantity = Integer.valueOf(binding.quantity.getText().toString().trim());

                    viewModel.createAssetPembelianBaru(date,name,price,quantity);
                } else {
                    if ( binding.usiaDepresiasi.getText().toString().equals("")) {
                        viewModel.getOnError().setValue("Data tidak lengkap");
                        return;
                    }
                    Integer usiaDepresiasi = Integer.valueOf(binding.usiaDepresiasi.getText().toString().trim());

                    viewModel.createAssetSudahAda(date, name, price, usiaDepresiasi);
                }
            }
        });
    }

    private void configureIntent() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(TAMBAH_ASSET_CATEG_NAME);
        binding.kategoriAsset.setText(name);

        int categ_id = intent.getIntExtra(TAMBAH_ASSET_CATEG_ID, 0);
        viewModel.setCateg_id(categ_id);
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                    Glide.with(this).load(selectedImage).apply(RequestOptions.circleCropTransform()).into(binding.fotoDetailAsset);
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
                    Glide.with(this).load(photo).apply(RequestOptions.circleCropTransform()).into(binding.fotoDetailAsset);
                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year + "-" + (month + 1) + "-" + day;
        binding.tanggalTambahAsset.setText(StringHelper.formatDatePicker(date));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v == binding.searchSupplier) {
            menu.setHeaderTitle("Supplier");
            for (int i = 0; i < viewModel.getSuppliers().getValue().size(); i++) {
                menu.add(0, i, 0, viewModel.getSuppliers().getValue().get(i).getName());
            }
        } else if (v == binding.pilihPo) {
            menu.setHeaderTitle("PO");
            for (int i = 0; i < viewModel.getPurchases().getValue().size(); i++) {
                menu.add(1, i, 0, viewModel.getPurchases().getValue().get(i).getName());
            }
        } else if (v == binding.pilihProduct) {
            menu.setHeaderTitle("PO Line (Product)");
            for (int i = 0; i < viewModel.getPoLines().getValue().size(); i++) {
                menu.add(2, i, 0, viewModel.getPoLines().getValue().get(i).getName());
            }
        } else if (v == binding.fotoDetailAsset) {
            menu.setHeaderTitle("Select Image");
            menu.add(9, 0, 0, "Camera");
            menu.add(9, 1, 0, "Gallery");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == 0) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.searchSupplier.getWindowToken(), 0);
            binding.radioPembelianBaru.setChecked(true);
            binding.radioSudahAda.setChecked(false);
            viewModel.getSelectedSupplier().setValue(viewModel.getSuppliers().getValue().get(item.getItemId()));
        } else if (item.getGroupId() == 1) {
            viewModel.getSelectedPurchase().setValue(viewModel.getPurchases().getValue().get(item.getItemId()));
        } else if (item.getGroupId() == 2) {
            viewModel.getSelectedPOLines().setValue(viewModel.getPoLines().getValue().get(item.getItemId()));
        } else if (item.getGroupId() == 9) {
            if (item.getItemId() == 0) {
                openCamera();
            } else {
                pickImage();
            }
        }

        return super.onContextItemSelected(item);
    }
}