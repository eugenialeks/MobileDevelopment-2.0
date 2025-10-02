package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.data.repository.AuthRepositoryImpl;
import ru.mirea.golysheva.skincare.data.repository.MIREpositoryTFLite;
import ru.mirea.golysheva.skincare.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.skincare.domain.models.Product;
import ru.mirea.golysheva.skincare.domain.models.SkinTypeResult;
import ru.mirea.golysheva.skincare.domain.models.User;
import ru.mirea.golysheva.skincare.domain.usecases.DetectSkinType;
import ru.mirea.golysheva.skincare.domain.usecases.GetProductById;
import ru.mirea.golysheva.skincare.domain.usecases.GetProductList;
import ru.mirea.golysheva.skincare.domain.usecases.LoginUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoginUser loginUC;
    private GetProductList getListUC;
    private GetProductById getByIdUC;
    private DetectSkinType detectUC;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUC   = new LoginUser(new AuthRepositoryImpl());
        getListUC = new GetProductList(new ProductRepositoryImpl());
        getByIdUC = new GetProductById(new ProductRepositoryImpl());
        detectUC  = new DetectSkinType(new MIREpositoryTFLite());

        tv = findViewById(R.id.textView);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnList  = findViewById(R.id.btnList);
        Button btnById  = findViewById(R.id.btnById);
        Button btnML    = findViewById(R.id.btnDetect);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                User u = loginUC.execute("demo@skincare.app", "123456");
                tv.setText("Login OK, token=" + u.getToken());
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                List<Product> list = getListUC.execute();
                tv.setText("Products: " + list.size() + " — " + list.get(0).getTitle());
            }
        });

        btnById.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Product p = getByIdUC.execute("p2");
                tv.setText(p == null ? "not found" : "ById: " + p.getTitle());
            }
        });

        btnML.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                SkinTypeResult r = detectUC.execute(new byte[0]);
                tv.setText("Skin: " + r.getType());
            }
        });
    }
}
