package ru.mirea.golysheva.data.storage.favorite;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.mirea.golysheva.data.storage.models.ProductEntity;

@Database(
        entities = {
                ProductEntity.class,
                ProductFavoriteEntity.class
        },
        version = 4,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ProductDao productDao();
    public abstract FavoriteDao favDao();

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    ctx.getApplicationContext(),
                                    AppDatabase.class,
                                    "skin_care.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
