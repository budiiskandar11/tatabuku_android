package com.tatabuku.app.ui.rekening.rincian_biaya;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.databinding.ActivityRincianBiayaBinding;
import com.tatabuku.app.model.rekening.CatatBiayaList;
import com.tatabuku.app.model.rekening.PostedBiayaHeader;
import com.tatabuku.app.model.rekening.PostedBiayaList;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.rekening.catat_biaya.CatatBiayaActivity;
import com.tatabuku.app.ui.rekening.detail.RekeningDetailActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class RincianBiayaActivity extends BaseActivity {

    public final static String BANK_FROM = "BANK_FROM";
    public final static String BANK_NAME = "BANK_NAME";
    public final static String TYPE = "TYPE";
    public final static String SELECTED_DATE = "SELECTED_DATE";
    public final static String SELECTED_LISTED_BIAYA = "SELECTED_LISTED_BIAYA";
    public final static String EDITED_VOUCHER_ID = "EDITED_VOUCHER_ID";
    public final static String EDITED_VOUCHER_NO = "EDITED_VOUCHER_NO";
    public final static String POSTED_VOUCHER_ID = "POSTED_VOUCHER_ID";

    private ActivityRincianBiayaBinding binding;
    private LinearLayoutManager layoutManager;
    private RincianBiayaListAdapter listAdapter;
    private RincianBiayaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        setupList();
        configureView();

        configureIntent();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RincianBiayaViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getHeader().observe(this, new Observer<PostedBiayaHeader>() {
            @Override
            public void onChanged(PostedBiayaHeader header) {
                binding.date.setText(StringHelper.formatInvoiceDate(header.getDate()));
                binding.voucher.setText(header.getVoucherNo());
            }
        });

        viewModel.getBiayaList().observe(this, new Observer<List<PostedBiayaList>>() {
            @Override
            public void onChanged(List<PostedBiayaList> postedBiayaLists) {
                Double amount = Double.valueOf(0);
                for (PostedBiayaList biaya : postedBiayaLists) {
                    amount = amount + biaya.getAmount();
                }
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getSubtotal().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double subtotal) {
                binding.textSubtotal.setText(StringHelper.numberFormatWithDecimal(subtotal));
            }
        });

        viewModel.getCatatBiayaList().observe(this, new Observer<ArrayList<CatatBiayaList>>() {
            @Override
            public void onChanged(ArrayList<CatatBiayaList> catatBiayaLists) {
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                Intent intent = new Intent(RincianBiayaActivity.this, RekeningDetailActivity.class);
                intent.putExtra(RekeningDetailActivity.SELECTED_REKENING_NAME, viewModel.getBank_name());
                intent.putExtra(RekeningDetailActivity.SELECTED_REKENING_ID, viewModel.getFrom_bank());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });

        viewModel.getType().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("pemasukan")) {
                    binding.title.setText("Rincian Penerimaan Uang");
                }
            }
        });
    }

    private void setupView() {
        binding = ActivityRincianBiayaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buttonBatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buttonPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                viewModel.saveBiaya();
            }
        });

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RincianBiayaActivity.this, CatatBiayaActivity.class);
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_BANK_NAME, viewModel.getBank_name());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_BANK_ID, viewModel.getFrom_bank());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_TYPE, viewModel.getType().getValue());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_EDIT_BIAYA, viewModel.getCatatBiayaForEdit());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_VOUCHER_NO, viewModel.getHeader().getValue().getVoucherNo());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_VOUCHER_ID, viewModel.getHeader().getValue().getId());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_EDIT_DATE, binding.date.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new RincianBiayaListAdapter(viewModel);
        binding.list.setAdapter(listAdapter);
    }

    private void setButtonVisibility(Boolean isEdit) {
        if (isEdit) {
            binding.buttonEdit.setVisibility(View.VISIBLE);
            binding.buttonPosting.setVisibility(View.GONE);
            binding.buttonSpacing.setVisibility(View.GONE);
        } else {
            binding.buttonEdit.setVisibility(View.INVISIBLE);
            binding.buttonPosting.setVisibility(View.VISIBLE);
            binding.buttonSpacing.setVisibility(View.VISIBLE);
        }
    }

    private void configureIntent() {
        Intent intent = getIntent();

        int bank_from = intent.getIntExtra(BANK_FROM, 0);
        String bank_name = intent.getStringExtra(BANK_NAME);
        if (intent.hasExtra(TYPE)) {
            String type = intent.getStringExtra(TYPE);
            viewModel.getType().setValue(type);
        }
        viewModel.setBank_name(bank_name);
        viewModel.setFrom_bank(bank_from);

        if (intent.hasExtra(SELECTED_DATE) && intent.hasExtra(SELECTED_LISTED_BIAYA) && intent.hasExtra(BANK_FROM)) {
            String date = intent.getStringExtra(SELECTED_DATE);
            ArrayList<CatatBiayaList> catatBiayaList = (ArrayList<CatatBiayaList>) intent.getSerializableExtra(SELECTED_LISTED_BIAYA);

            binding.date.setText(date);
            setButtonVisibility(false);
            viewModel.setPageType(RincianBiayaViewModel.PageType.CATAT_BIAYA);

            if (intent.hasExtra(EDITED_VOUCHER_ID)) {
                String voucher_no = intent.getStringExtra(EDITED_VOUCHER_NO);
                int voucher_id = intent.getIntExtra(EDITED_VOUCHER_ID,0);
                binding.voucher.setText(voucher_no);
                viewModel.setEdit_voucher_id(voucher_id);
                viewModel.setPageType(RincianBiayaViewModel.PageType.EDIT_BIAYA);
            }

            viewModel.setCatatBiayaData(date, catatBiayaList);
        } else {
            String voucher_id = intent.getStringExtra(POSTED_VOUCHER_ID);
            int int_voucher_id = Integer.parseInt(voucher_id);
            setButtonVisibility(true);
            viewModel.setPageType(RincianBiayaViewModel.PageType.RINCIAN_BIAYA);
            viewModel.fetchPostedBiaya(int_voucher_id);
        }
    }
}