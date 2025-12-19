# Практическая работа №15

**Тема:** Реализация навигации в Android-приложении (`Bottom Navigation`, `Navigation Drawer`, `Navigation Component`).

## Цель работы

Изучить принципы построения навигации в Android, освоить работу с компонентами `BottomNavigationView` и `NavigationDrawer`, а также внедрить библиотеку `Android Jetpack Navigation Component` в основной проект.
  
---

## Реализация Bottom Navigation (Приложение «Кофе-Брейк»)

**Создание модуля:** Был создан новый модуль `BottomNavigationApp` с использованием шаблона `Bottom Navigation Views Activity`.
**Настройка ресурсов:**
- В `colors.xml` обновлена цветовая палитра на кофейные тона (коричневый, бежевый).
- Добавлены векторные иконки (Vector Assets) для меню: чашка кофе, акция, корзина.
**Настройка меню:** В файле `bottom_nav_menu.xml` пункты меню привязаны к новым иконкам и переименованы в «Меню», «Акции», «Корзина».
**Реализация фрагментов:** Из классов фрагментов удалена шаблонная привязка к ViewModel, реализована прямая установка текста через XML-разметку.

<img width="1280" height="796" alt="image" src="https://github.com/user-attachments/assets/d3e51803-adfe-4f27-8f56-a6740b82934b" />

<img width="1280" height="796" alt="image" src="https://github.com/user-attachments/assets/6cb1f452-c872-45ee-98b0-3a5f13466f08" />

<img width="1280" height="806" alt="image" src="https://github.com/user-attachments/assets/ea9be8bb-a006-4d35-b3ce-4a9412cf486c" />

---

## Реализация Navigation Drawer (Приложение «Travel Guide»)

**Создание модуля:** Создан модуль `NavigationDrawerApp`.
**Дизайн:** Цветовая гамма изменена на «морскую» (синие и желтые оттенки).
**Настройка Header:** В файле `nav_header_main.xml` изменен фон, установлено название приложения «Travel Guide» и email пользователя.
**Меню шторки:** Добавлены пункты «Куда поехать», «Мои поездки», «Билеты» с соответствующими иконками.
**Обработка навигации:** В `MainActivity` добавлен OnBackPressedCallback, который закрывает шторку при нажатии системной кнопки «Назад», вместо закрытия всего приложения.

<img width="1280" height="795" alt="image" src="https://github.com/user-attachments/assets/f3c73759-9f5a-4078-b411-33ba2b220e83" />

<img width="1280" height="798" alt="image" src="https://github.com/user-attachments/assets/ef943538-9ccc-419f-a2be-2d1af76fada7" />

<img width="1280" height="793" alt="image" src="https://github.com/user-attachments/assets/925159d5-23c4-4b03-9461-7d7f6ad88433" />

<img width="1280" height="792" alt="image" src="https://github.com/user-attachments/assets/576ae0ad-ffc6-40d0-b961-813b3bc2c865" />

---

## Контрольное задание

### Внедрение Navigation Component в основной проект (SkinCare)

1. Подключение зависимостей: В `build.gradle.kts` добавлены библиотеки `androidx.navigation:navigation-fragment` и `androidx.navigation:navigation-ui`.
2. Создание графа навигации:
- Создан файл `mobile_navigation.xml`.
- Определены фрагменты: `HomeFragment`, `CatalogFragment`, `ScanFragment`, `FavoritesFragment`, `ProfileFragment`.
- Настроены аргументы (argument) для передачи category_id в каталог.
- Добавлен action для перехода с главного экрана в каталог.
3. Интеграция в Layout: В `activity_main.xml` контейнер `FrameLayout` заменен на `androidx.fragment.app.FragmentContainerView`, указан `navGraph`.
4. Связывание с кодом:
- В MainActivity настроен NavController.
- Метод setupWithNavController заменен на кастомный слушатель setOnItemSelectedListener для сохранения логики блокировки раздела «Сканирование» для неавторизованных пользователей (гостей).
- Для авторизованных пользователей управление передается методу NavigationUI.onNavDestinationSelected.
5. Передача данных: В HomeFragment реализована навигация через NavController с передачей Bundle при клике на категорию.

<img width="1280" height="746" alt="image" src="https://github.com/user-attachments/assets/03a3fb4a-e3d7-495c-9a21-d9172090448e" />

--- 

## Итоги

В ходе работы была реализована навигация в Android-приложениях тремя способами. Были изучены компоненты `BottomNavigationView` и `NavigationDrawer`, а также произведена миграция управления фрагментами на современный подход `Jetpack Navigation Component`. Это позволило упростить код `MainActivity`, избавиться от ручных транзакций `FragmentManager` и визуализировать карту переходов в приложении.

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
