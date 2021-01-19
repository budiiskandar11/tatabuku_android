package com.tatabuku.app.ui.titip_journal.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.tatabuku.app.databinding.ActivityTitipJournalBinding;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalHeader;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalModel;
import com.tatabuku.app.ui.titip_journal.tambah.TambahTitipJournalActivity;

import java.util.List;


public class TitipJournalActivity extends AppCompatActivity {

    private ActivityTitipJournalBinding binding;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager newlayoutManager;
    private TitipJournalListAdapter listAdapter;
    private TitipJurnalViewModel viewModel;
    private TitipJournalBaruListAdapter newlistAdapter;

    private String lastKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        setupList();
        configureViewModel();
        configureView();
        viewModel.fetchDetailJurnal("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchDetailJurnal("");
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TitipJurnalViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getJurnalHeader().observe(this, new Observer<DetailTitipJurnalHeader>() {
            @Override
            public void onChanged(DetailTitipJurnalHeader header) {
                binding.titipQuota.setText(String.valueOf(header.getQuotaJournalTitipan().intValue()));
                binding.titipTerpakai.setText(String.valueOf(header.getQuotaTerpakai().intValue()));
                binding.titipSisa.setText(String.valueOf(header.getQuotaSisa().intValue()));

                Double presentase = header.getQuotaTerpakai() / header.getQuotaJournalTitipan() * 100 ;
                binding.titipProsentase.setText(String.valueOf(presentase.intValue()) + "%");
                binding.progressBartitip.setProgress(presentase.intValue());
            }
        });

        viewModel.getJurnalList().observe(this, new Observer<List<DetailTitipJurnalModel>>() {
            @Override
            public void onChanged(List<DetailTitipJurnalModel> detailTitipJurnalModels) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });

        viewModel.getJurnalListbelum().observe(this, new Observer<List<DetailTitipJurnalModel>>() {
            @Override
            public void onChanged(List<DetailTitipJurnalModel> detailTitipJurnalModels) {
                newlistAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setupView() {
        binding = ActivityTitipJournalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView(){

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastKeyword = binding.search.getText().toString();
                viewModel.fetchDetailJurnal(lastKeyword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.fetchDetailJurnal(lastKeyword);
            }
        });

        binding.buatTitipJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitipJournalActivity.this, TambahTitipJournalActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupList(){
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.listTransaksi.setLayoutManager(layoutManager);
        listAdapter = new TitipJournalListAdapter(viewModel);
        binding.listTransaksi.setAdapter(listAdapter);

        newlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.listTransaksiBaru.setLayoutManager(newlayoutManager);
        newlistAdapter = new TitipJournalBaruListAdapter(viewModel);
        binding.listTransaksiBaru.setAdapter(newlistAdapter);

    }

}