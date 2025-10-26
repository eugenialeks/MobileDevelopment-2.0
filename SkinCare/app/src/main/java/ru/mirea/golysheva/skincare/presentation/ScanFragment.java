package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import ru.mirea.golysheva.skincare.R;

public class ScanFragment extends Fragment {
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_scan, c, false);
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        com.google.android.material.appbar.MaterialToolbar tb = v.findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(
                click -> requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );
        v.findViewById(R.id.btnPick).setOnClickListener(view -> {});
        v.findViewById(R.id.btnCamera).setOnClickListener(view -> {});
    }
}
