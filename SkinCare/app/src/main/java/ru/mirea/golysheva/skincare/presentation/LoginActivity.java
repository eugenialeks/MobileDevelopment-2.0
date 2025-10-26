package ru.mirea.golysheva.skincare.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.domain.models.User;
import ru.mirea.golysheva.domain.repository.AuthRepository;
import ru.mirea.golysheva.domain.usecases.LoginAsGuest;
import ru.mirea.golysheva.domain.usecases.auth.LoginUser;
import ru.mirea.golysheva.domain.usecases.auth.RegisterUser;
import ru.mirea.golysheva.data.repository.AuthRepositoryImpl;
import ru.mirea.golysheva.data.storage.sharedprefs.ClientPrefs;

public class LoginActivity extends AppCompatActivity {

    private LoginUser loginUC;
    private RegisterUser registerUC;
    private LoginAsGuest guestUC;

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin, btnGuest;
    private TextView tvRegister;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // DI
        AuthRepository repo = new AuthRepositoryImpl(new ClientPrefs(this));
        loginUC    = new LoginUser(repo);
        registerUC = new RegisterUser(repo);
        guestUC    = new LoginAsGuest(repo);

        tilEmail    = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        btnGuest    = findViewById(R.id.btnGuest);
        tvRegister  = findViewById(R.id.tvRegister);

        // кнопки
        btnLogin.setOnClickListener(v -> doLogin());
        btnGuest.setOnClickListener(v -> doGuest());

        // кликабельное "Зарегистрироваться"
        setupRegisterLink();
    }

    private void setupRegisterLink() {
        String prefix = getString(R.string.register_prompt_prefix);
        String action = getString(R.string.register_prompt_action);

        SpannableString sp = new SpannableString(prefix + action);
        final int linkColor = ContextCompat.getColor(this, R.color.sc_link);

        ClickableSpan cs = new ClickableSpan() {
            @Override public void onClick(@NonNull View widget) {
                doRegister();
            }
            @Override public void updateDrawState(@NonNull android.text.TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(linkColor);      // цвет «Зарегистрироваться»
                ds.setUnderlineText(true);   // подчёркивание как на макете (можно false, если не нужно)
            }
        };

        int start = prefix.length();
        int end   = start + action.length();
        sp.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvRegister.setText(sp, TextView.BufferType.SPANNABLE);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
        tvRegister.setHighlightColor(0x00000000); // без серой подсветки при клике
    }

    private void doLogin() {
        if (!validateInputs()) return;
        setEnabled(false);
        final String email = txt(etEmail);
        final String pass  = txt(etPassword);
        loginUC.execute(email, pass, new AuthRepository.Callback() {
            @Override public void onSuccess(User user) { goHome(); }
            @Override public void onError(Throwable t) { onFail(t); }
        });
    }

    private void doRegister() {
        if (!validateInputs()) return;
        setEnabled(false);
        final String email = txt(etEmail);
        final String pass  = txt(etPassword);
        registerUC.execute(email, pass, new AuthRepository.Callback() {
            @Override public void onSuccess(User user) {
                // сразу логиним по тем же данным
                loginUC.execute(email, pass, new AuthRepository.Callback() {
                    @Override public void onSuccess(User u) { goHome(); }
                    @Override public void onError(Throwable t) { onFail(t); }
                });
            }
            @Override public void onError(Throwable t) { onFail(t); }
        });
    }

    private void doGuest() {
        setEnabled(false);
        guestUC.execute(new AuthRepository.Callback() {
            @Override public void onSuccess(User user) { goHome(); }
            @Override public void onError(Throwable t) { onFail(t); }
        });
    }

    // --- helpers ---
    private boolean validateInputs() {
        clearErrors();
        String email = txt(etEmail);
        String pass  = txt(etPassword);

        boolean ok = true;
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.hint_email));
            ok = false;
        }
        if (TextUtils.isEmpty(pass)) {
            tilPassword.setError(getString(R.string.hint_password));
            ok = false;
        }
        if (!ok) toast(R.string.enter_email_password);
        return ok;
    }

    private void clearErrors() {
        tilEmail.setError(null);
        tilPassword.setError(null);
    }

    private void goHome() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void onFail(Throwable t) {
        setEnabled(true);
        toast(getString(R.string.error_fmt, t.getMessage()));
    }

    private void toast(int resId) { Toast.makeText(this, resId, Toast.LENGTH_SHORT).show(); }
    private void toast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
    private String txt(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
    private void setEnabled(boolean e) {
        btnLogin.setEnabled(e);
        btnGuest.setEnabled(e);
        tvRegister.setEnabled(e);
    }
}
