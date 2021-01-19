package com.tatabuku.app.ui.pembelian.tambah.produk;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityTambahProdukBinding;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.LoadProductData;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.tambah.supplier.TambahSupplierActivity;
import com.tatabuku.app.util.ImageHelper;
import com.tatabuku.app.util.StringHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TambahProdukActivity extends BaseActivity {
    public static final String ARG_PRODUCT_ID = "ARG_PRODUCT_ID";

    private ActivityTambahProdukBinding binding;
    private TambahProdukViewModel viewModel;
    private List<Category> categories = new ArrayList<>();
    private File selectedFile = null;
    private int productId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productId = getIntent().getIntExtra(ARG_PRODUCT_ID, 0);
        setupViewModel();
        configureViewModel();
        setupView();
        configureView();
        viewModel.fetchCategory();

        if (productId != 0) {
            viewModel.loadProduct(productId);
            binding.title.setText(R.string.edit_produk);
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TambahProdukViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getProductData().observe(this, new Observer<LoadProductData>() {
            @Override
            public void onChanged(LoadProductData loadProductData) {
                if(!loadProductData.getName().equalsIgnoreCase("false")) {
                    binding.name.setText(loadProductData.getName());
                }
                if(!loadProductData.getDefaultCode().equalsIgnoreCase("false")) {
                    binding.sku.setText(loadProductData.getDefaultCode());
                }
                if(!loadProductData.getDescription().equalsIgnoreCase("false")) {
                    binding.description.setText(loadProductData.getDescription());
                }
                binding.hargaJual.setText(loadProductData.getListPrice().intValue() + "");
                binding.hargaBeli.setText(loadProductData.getPurchasePrice().intValue() + "");
                binding.minStok.setText(loadProductData.getMinStock().intValue() + "");
                binding.maxStok.setText(loadProductData.getMaxStock().intValue() + "");

                Glide.with(TambahProdukActivity.this).asBitmap()
                        .load(Base64.decode(loadProductData.getImage(), Base64.DEFAULT)).into(binding.image);
            }
        });

        viewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {
                categories.clear();
                categories.addAll(categoryList);
            }
        });

        viewModel.getSelectedCategory().observe(this, new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                binding.kategory.setText(category.getName());
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
        binding = ActivityTambahProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        registerForContextMenu(binding.kategory);
        binding.kategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(binding.kategory);
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
                viewModel.saveProduk(productId,
                        binding.name.getText().toString(),
                        binding.sku.getText().toString(),
                        binding.description.getText().toString(),
                        binding.minStok.getText().toString(),
                        binding.maxStok.getText().toString(),
                        binding.hargaBeli.getText().toString(),
                        binding.hargaJual.getText().toString(),
                        selectedFile);
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
                    Glide.with(this).load(selectedImage).into(binding.image);
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
                    Glide.with(this).load(photo).into(binding.image);
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == binding.kategory) {
            menu.setHeaderTitle(getString(R.string.kategori_produk));
            for (int i = 0; i < categories.size(); i++) {
                menu.add(0, i, 0, categories.get(i).getName());
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
            viewModel.getSelectedCategory().setValue(categories.get(item.getItemId()));
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