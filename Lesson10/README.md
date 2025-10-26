# Практическая работа №10

**Тема:** модульность (data/domain/app), создание прототипа приложения SkinCare, авторизация через Firebase Auth, три способа обработки данных в репозитории: SharedPreferences, Room, NetworkApi (mock).
## Цель работы

- Выделить модули domain и data, разнести туда бизнес-интерфейсы и реализацию.

- В app оставить только Android-UI, навигацию и связывание слоёв.

- Создать прототип приложения SkinCare

- Добавить страницу авторизации с Firebase Auth и развести логику по трём модулям.

- В репозиториях использовать три способа хранения/получения данных:

  - SharedPreferences (информация о клиенте),

  - Room (локальный кэш/избранное/товары),

  - NetworkApi c замоканными данными (эмуляция сети).
  
---

## Архитектура и модули

### 1) Модуль `domain`

Содержит только чистую Java-логику:

- `models/Movie` — доменная сущность (id, name).

- `repository/MovieRepository` — интерфейс с методами:

  - boolean saveMovie(Movie movie);
  
  - Movie getMovie().

- `usecases/SaveMovieToFavoriteUseCase` — принимает MovieRepository, инкапсулирует сценарий сохранения.

- `usecases/GetFavoriteFilmUseCase` — возвращает текущий «любимый» фильм.

В `domain` нет зависимостей на Android-SDK и на конкретные реализации.

### 2) Модуль `data`

Реализации и источники данных:

- `repository/MovieRepositoryImpl` — реализует MovieRepository, но не работает напрямую с SharedPreferences.

  - Использует интерфейс хранилища storage/sharedprefs/MovieStorage.
  
  - Внутри выполняет маппинг между domain.models.Movie ↔ data.storage.models.Movie.

- `storage/sharedprefs/MovieStorage` — интерфейс локального источника.

- `storage/sharedprefs/SharedPrefMovieStorage `— конкретная реализация на базе SharedPreferences.

- `storage/models/Movie` — модель уровня хранения (id, name, localDate).

Таким образом, MovieRepositoryImpl стал «роутером» между доменом и источниками данных. Логику доступа к конкретному источнику (SP) инкапсулирует SharedPrefMovieStorage.

### 3) Модуль app (presentation)

- `presentation/MainActivity` — простая форма:

  - поле ввода названия фильма,
  
  - кнопка «Сохранить любимый фильм»,
  
  - кнопка «Отобразить любимый фильм».

- Здесь же создаётся граф зависимостей вручную:

```java
MovieStorage storage = new SharedPrefMovieStorage(this);
MovieRepository repository = new MovieRepositoryImpl(storage);
SaveMovieToFavoriteUseCase saveUC = new SaveMovieToFavoriteUseCase(repository);
GetFavoriteFilmUseCase getUC = new GetFavoriteFilmUseCase(repository);
```

- Кнопки вызывают `saveUC.execute(Movie)` и `getUC.execute()`.
---

## Раздельные модели и мапперы

Чтобы слой `data` был переносимым и независимым, внедрены две модели:

- `domain.models.Movie` — используется в use case’ах и UI;

- `data.storage.models.Movie` — используется внутри SharedPrefMovieStorage.

Маппинг выполняет `MovieRepositoryImpl`:

```java
// data → domain
private ru.mirea.golysheva.domain.models.Movie mapToDomain(
        ru.mirea.golysheva.data.storage.models.Movie m) {
    return new ru.mirea.golysheva.domain.models.Movie(m.getId(), m.getName());
}

// domain → data
private ru.mirea.golysheva.data.storage.models.Movie mapToStorage(
        ru.mirea.golysheva.domain.models.Movie m) {
    return new ru.mirea.golysheva.data.storage.models.Movie(
        1, m.getName(), LocalDate.now().toString()
    );
}
```

---

## Работа с SharedPreferences (инкапсуляция в data)

SharedPrefMovieStorage хранит/читает данные:

```java
@Override
public Movie get() {
    String name = sp.getString(KEY, "unknown");
    String date = sp.getString(DATE_KEY, LocalDate.now().toString());
    int id = sp.getInt(ID_KEY, -1);
    return new Movie(id, name, date);
}

@Override
public boolean save(Movie movie) {
    sp.edit()
      .putString(KEY, movie.getName())
      .putString(DATE_KEY, LocalDate.now().toString())
      .putInt(ID_KEY, 1)
      .commit();
    return true;
}
```

Вся Android-специфика остаётся в `data`. `domain` и `presentation` не знают, где хранятся данные.

<img width="974" height="785" alt="image" src="https://github.com/user-attachments/assets/2bdb85a2-f136-4be9-8b41-57ca4d2528e6" />

<img width="974" height="616" alt="image" src="https://github.com/user-attachments/assets/5eeae01c-d953-4652-93f7-9bb62f6108ff" />

---

## Спроектированный дизайн приложения SkinCare

<img width="669" height="1516" alt="image" src="https://github.com/user-attachments/assets/e15b0d32-c34d-4255-8749-63ef42f8e688" />

<img width="669" height="1516" alt="image" src="https://github.com/user-attachments/assets/7b9e4f0c-13aa-4e3a-8411-fb17eefcbd45" />

<img width="669" height="1516" alt="image" src="https://github.com/user-attachments/assets/8b9cca9c-9f39-499b-8901-5ef6fad0b39b" />

<img width="669" height="1516" alt="image" src="https://github.com/user-attachments/assets/52840ce6-2af0-43c9-b6bc-1dd5c206985b" />

<img width="669" height="1516" alt="image" src="https://github.com/user-attachments/assets/3c411008-4d9b-46eb-b5f0-58ca0bcb767f" />

<img width="577" height="1516" alt="image" src="https://github.com/user-attachments/assets/77b54eb3-75de-40f6-a208-d16124d8941f" />

---

## Декомпозиция на модули

### Структура проекта

- :domain — «чистый» модуль (Java/Kotlin library, без Android).
Содержит: entities, repository contracts, use cases, error/value objects.

- :data — Android-модуль (Android library).
Содержит: реализации репозиториев, источники данных (Room DAO, SharedPrefs storage, NetworkApi (mock)), мапперы.

- :app — презентационный модуль (UI, навигация, DI/Service-locator, ViewModels/Presenters, Activities/Fragments).

<img width="703" height="1518" alt="image" src="https://github.com/user-attachments/assets/670c5457-807f-427b-b878-17a1ffabe0bc" />

---

## Новая активити авторизации (Firebase Auth)

Связка слоёв в LoginActivity:

```java
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
```

<img width="974" height="809" alt="image" src="https://github.com/user-attachments/assets/cff2e261-0d2a-441e-b58e-132aa6e3e6e9" />

<img width="974" height="815" alt="image" src="https://github.com/user-attachments/assets/5e298941-4b07-4af5-8f62-0cbbe9e1c4e3" />

---

## Три способа обработки данных (репозитории)

1. `SharedPreferences` — «Информация о клиенте»

- Реализация: `ClientPrefsImpl` (модуль `data`).

- Использование: в `LoginActivity`, в настройках/профиле.

- Причина: мало полей, нет сложных связей, нужен быстрый доступ при старте.

2. `Room` — локальное хранилище/кэш

- Продукты (`ProductDao`) и «Избранное» (`FavoritesDao`).

- Каталог/детали/избранное работают из Room; при первом запуске — заполняется из FakeNetworkApi.

3. NetworkApi (`FakeNetworkApi`) — эмуляция сети

- Отдаёт замоканный список продуктов (id, имя, цена, imageResName, описания секций и т.д.).

- Репозиторий (`ProductRepositoryImpl`) сам решает: взять из Room или дотянуть «сеть» и обновить Room.

**Типичный пользовательский поток**:

- Каталог: `GetProductList(repo).execute()` → репозиторий → `Room or FakeNetworkApi` → `Room` → UI.

- Избранное: `FavoritesRepository.toggle(id)` и `getAllIds()` (для фильтрации списка продуктов в избранном).

- Иконка избранного подкрашивается мгновенно (оптимистично) в адаптере.

<img width="974" height="815" alt="image" src="https://github.com/user-attachments/assets/5863795e-2a30-4799-bfe1-4888b538290e" />

<img width="974" height="813" alt="image" src="https://github.com/user-attachments/assets/7ed1cd1d-cde8-4126-ab7e-134158f08112" />

<img width="974" height="809" alt="image" src="https://github.com/user-attachments/assets/1c56e49e-adb2-4a90-a70a-e1f8f2ce6911" />

<img width="974" height="811" alt="image" src="https://github.com/user-attachments/assets/4a020e3d-9b8e-4931-bd6e-6a8e8d038d13" />

<img width="974" height="801" alt="image" src="https://github.com/user-attachments/assets/0648a641-13e2-4bfe-919b-78443d5d5557" />

<img width="974" height="812" alt="image" src="https://github.com/user-attachments/assets/a86cbca2-dd83-4f1a-94c2-9e66d22ae84c" />

---

##Итоги

- Проект разделён на `:domain`, `:data`, `:app`; настроены зависимости модулей.

- Реализована страница авторизации с `Firebase Auth`; логика разнесена по слоям: `UI` → `use-cases (domain)` → `AuthRepositoryImpl (data)`.

- В репозиториях реализованы три способа обработки данных:

  - `SharedPreferences` — профиль клиента;
  
  - `Room` — основное локальное хранилище сущностей;
  
  - `NetworkApi (mock)` — источник «сети» с маппингом DTO → Entity → Domain.

- Домен полностью изолирован от Android/Firebase, что соответствует принципам Clean Architecture и требованиям задания.

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
