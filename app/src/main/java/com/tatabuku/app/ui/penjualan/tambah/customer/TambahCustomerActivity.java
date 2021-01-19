package com.tatabuku.app.ui.penjualan.tambah.customer;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityTambahCustomerBinding;
import com.tatabuku.app.model.pembelian.CityData;
import com.tatabuku.app.model.pembelian.PaymentTerm;
import com.tatabuku.app.model.pembelian.ProvinceData;
import com.tatabuku.app.model.pembelian.SubdistrictData;
import com.tatabuku.app.model.penjualan.LoadCustomerData;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.tambah.supplier.TambahSupplierActivity;
import com.tatabuku.app.util.ImageHelper;
import com.tatabuku.app.util.StringHelper;

import java.io.File;

public class TambahCustomerActivity extends BaseActivity {
    public static final String ARG_PARTNER_ID = "ARG_PARTNER_ID";

    private ActivityTambahCustomerBinding binding;
    private TambahCustomerViewModel viewModel;
    private File selectedFile = null;
    private String currentValue = "";
    private String npwpString = "";
    private int partnerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partnerId = getIntent().getIntExtra(ARG_PARTNER_ID, 0);

        setupViewModel();

        setupView();
        configureView();
        configureViewModel();

        viewModel.fetchPaymentTerms();
        viewModel.fetchProvince();
        if (partnerId != 0) {
            viewModel.fetchCustomerData(partnerId);
            binding.title.setText(R.string.edit_customer);
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TambahCustomerViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getCustomerData().observe(this, new Observer<LoadCustomerData>() {
            @Override
            public void onChanged(LoadCustomerData loadCustomerData) {
                binding.namaCustomer.setText(loadCustomerData.getName());
                binding.alamat.setText(loadCustomerData.getStreet());
                binding.email.setText(loadCustomerData.getEmail());
                binding.phone.setText(loadCustomerData.getMobile());
                binding.postalCode.setText(loadCustomerData.getZip());
                binding.total.setText(StringHelper.numberFormat(loadCustomerData.getCreditLimit()));
                if (loadCustomerData.getStatusPajak().equalsIgnoreCase("pkp")) {
                    binding.pkp.setChecked(true);
                    binding.npwp.setText(loadCustomerData.getVat());
                } else {
                    binding.nonPkp.setChecked(true);
                }

                Glide.with(TambahCustomerActivity.this).asBitmap()
                        .load(Base64.decode(loadCustomerData.getImage(), Base64.DEFAULT))
                        .apply(RequestOptions.circleCropTransform()).into(binding.image);
            }
        });

        viewModel.getSelectedCity().observe(this, new Observer<CityData>() {
            @Override
            public void onChanged(CityData cityData) {
                if (cityData != null) {
                    binding.kota.setText(cityData.getName());
                    viewModel.fetchSubdisctrict(cityData.getId() + "");
                }
            }
        });

        viewModel.getSelectedProvince().observe(this, new Observer<ProvinceData>() {
            @Override
            public void onChanged(ProvinceData provinceData) {
                if (provinceData != null) {
                    binding.provinsi.setText(provinceData.getName());
                    viewModel.fetchCity(provinceData.getCode());
                }
            }
        });

        viewModel.getSelectedSubdistrict().observe(this, new Observer<SubdistrictData>() {
            @Override
            public void onChanged(SubdistrictData subdistrictData) {
                if (subdistrictData != null) {
                    binding.kecamatan.setText(subdistrictData.getName());
                }
            }
        });

        viewModel.getSelectedTerm().observe(this, new Observer<PaymentTerm>() {
            @Override
            public void onChanged(PaymentTerm paymentTerm) {
                if (paymentTerm != null) {
                    binding.termin.setText(paymentTerm.getName());
                }
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                finish();
            }
        });
    }

    private void setupView() {
        binding = ActivityTambahCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        registerForContextMenu(binding.termin);
        binding.termin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.termin);
            }
        });

        registerForContextMenu(binding.provinsi);
        binding.provinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.provinsi);
            }
        });

        registerForContextMenu(binding.kota);
        binding.kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.kota);
            }
        });

        registerForContextMenu(binding.kecamatan);
        binding.kecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.kecamatan);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                String statusPajak = "";
                switch (binding.statusPajak.getCheckedRadioButtonId()) {
                    case R.id.pkp:
                        statusPajak = "pkp";
                        break;
                    case R.id.non_pkp:
                        statusPajak = "non_pkp";
                        break;
                }
                if(partnerId == 0) {
                    viewModel.saveCustomer(binding.namaCustomer.getText().toString(),
                            binding.alamat.getText().toString(),
                            binding.email.getText().toString(),
                            binding.phone.getText().toString(),
                            binding.total.getText().toString(),
                            npwpString,
                            statusPajak,
                            binding.postalCode.getText().toString(),
                            selectedFile);
                } else {
                    viewModel.editCustomer(partnerId, binding.namaCustomer.getText().toString(),
                            binding.alamat.getText().toString(),
                            binding.email.getText().toString(),
                            binding.phone.getText().toString(),
                            binding.total.getText().toString(),
                            npwpString,
                            statusPajak,
                            binding.postalCode.getText().toString(),
                            selectedFile);
                }
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerForContextMenu(binding.image);
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.image);
            }
        });

        binding.npwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentValue)) {
                    binding.npwp.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[.\\-]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        String formatted = StringHelper.npwpFormat(cleanString);

                        npwpString = formatted;
                        binding.npwp.setText(formatted);
                        binding.npwp.setSelection(formatted.length());
                    } else {
                        npwpString = "";
                        binding.npwp.setText("");
                    }
                    binding.npwp.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.total.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(currentValue)) {
                    binding.total.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
                    if (!cleanString.equals("")) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = StringHelper.numberFormat(parsed);

                        currentValue = formatted;
                        binding.total.setText(formatted);
                        binding.total.setSelection(formatted.length());
                    } else {
                        currentValue = "";
                        binding.total.setText("");
                    }
                    binding.total.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                    selectedFile = file;
                    Glide.with(this).load(selectedImage).apply(RequestOptions.circleCropTransform()).into(binding.image);
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = ImageHelper.getImageUri(this, photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(ImageHelper.getUriRealPath(this, tempUri));

                    selectedFile = finalFile;
                    Glide.with(this).load(photo).apply(RequestOptions.circleCropTransform()).into(binding.image);
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == binding.termin) {
            menu.setHeaderTitle(getString(R.string.termin));
            for (int i = 0; i < viewModel.getPaymentTerms().getValue().size(); i++) {
                menu.add(0, i, 0, viewModel.getPaymentTerms().getValue().get(i).getName());
            }
        } else if (v == binding.provinsi) {
            menu.setHeaderTitle(getString(R.string.provinsi));
            for (int i = 0; i < viewModel.getProvinceList().getValue().size(); i++) {
                menu.add(1, i, 0, viewModel.getProvinceList().getValue().get(i).getName());
            }
        } else if (v == binding.kota) {
            menu.setHeaderTitle(getString(R.string.kota));
            for (int i = 0; i < viewModel.getCityList().getValue().size(); i++) {
                menu.add(2, i, 0, viewModel.getCityList().getValue().get(i).getName());
            }
        } else if (v == binding.kecamatan) {
            menu.setHeaderTitle(getString(R.string.kecamatan));
            for (int i = 0; i < viewModel.getSubdistrictList().getValue().size(); i++) {
                menu.add(3, i, 0, viewModel.getSubdistrictList().getValue().get(i).getName());
            }
        } else if (v == binding.image) {
            menu.setHeaderTitle("Select Image");
            menu.add(9, 0, 0, "Camera");
            menu.add(9, 1, 0, "Gallery");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == 0) {
            viewModel.getSelectedTerm().setValue(viewModel.getPaymentTerms().getValue().get(item.getItemId()));
        } else if (item.getGroupId() == 1) {
            viewModel.getSelectedProvince().setValue(viewModel.getProvinceList().getValue().get(item.getItemId()));
        } else if (item.getGroupId() == 2) {
            viewModel.getSelectedCity().setValue(viewModel.getCityList().getValue().get(item.getItemId()));
        } else if (item.getGroupId() == 3) {
            viewModel.getSelectedSubdistrict().setValue(viewModel.getSubdistrictList().getValue().get(item.getItemId()));
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