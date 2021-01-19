package com.tatabuku.app.ui.rekening.catat_biaya;

import com.tatabuku.app.model.rekening.CatatBiayaList;

public interface CatatBiayaListener {
    void onItemChanged(CatatBiayaList biaya);
    void onItemUnchecked(CatatBiayaList biaya);
}
