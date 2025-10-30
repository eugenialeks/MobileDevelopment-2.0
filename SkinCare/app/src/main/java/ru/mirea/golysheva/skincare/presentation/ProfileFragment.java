package ru.mirea.golysheva.skincare.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.presentation.profile.ProfileViewModel;
import ru.mirea.golysheva.skincare.presentation.profile.ProfileVmFactory;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, new ProfileVmFactory(requireContext()))
                .get(ProfileViewModel.class);

        TextView tvUserName = view.findViewById(R.id.tvUserName);
        TextView tvUserEmail = view.findViewById(R.id.tvUserEmail);
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);

        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tvUserName.setText(user.getName());
                tvUserEmail.setText(user.getEmail());
            }
        });

        viewModel.logoutEvent.observe(getViewLifecycleOwner(), hasLoggedOut -> {
            if (hasLoggedOut) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();

                viewModel.onLogoutEventHandled();
            }
        });

        btnLogout.setOnClickListener(v -> viewModel.logout());

        viewModel.loadUserData();
    }
}
