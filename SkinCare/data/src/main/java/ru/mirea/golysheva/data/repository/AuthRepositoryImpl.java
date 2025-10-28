package ru.mirea.golysheva.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.golysheva.data.storage.sharedprefs.ClientPrefs;
import ru.mirea.golysheva.domain.models.User;
import ru.mirea.golysheva.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    private final ClientPrefs prefs;
    private final FirebaseAuth auth;

    public AuthRepositoryImpl(ClientPrefs prefs) {
        this.prefs = prefs;
        this.auth  = FirebaseAuth.getInstance();
    }

    private static User map(FirebaseUser fu) {
        if (fu == null) return null;

        String email = fu.getEmail();
        if (email == null && fu.isAnonymous()) {
            email = "guest@skincare";
        }

        String displayName = fu.getDisplayName();
        if (displayName == null) {
            displayName = fu.isAnonymous() ? "Guest" : email;
        }

        return new User(email, fu.getUid(), displayName);
    }

    private void saveAndReturn(Task<AuthResult> task, Callback cb) {
        task.addOnSuccessListener(res -> {
            FirebaseUser fu = auth.getCurrentUser();
            User u = map(fu);
            if (u != null) {
                prefs.saveUser(u);
                cb.onSuccess(u);
            } else {
                cb.onError(new IllegalStateException("User is null after Firebase auth"));
            }
        }).addOnFailureListener(cb::onError);
    }

    @Override public void login(String email, String password, Callback cb) {
        saveAndReturn(auth.signInWithEmailAndPassword(email, password), cb);
    }

    @Override public void register(String email, String password, Callback cb) {
        saveAndReturn(auth.createUserWithEmailAndPassword(email, password), cb);
    }

    @Override public void loginAnonymously(Callback cb) {
        saveAndReturn(auth.signInAnonymously(), cb);
    }

    @Override public void logout() {
        auth.signOut();
        prefs.clear();
    }

    @Override public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override public User currentUser() {
        return map(auth.getCurrentUser());
    }
}
