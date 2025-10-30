#!/bin/bash

# Имя выходного файла
OUTPUT_FILE="project_context.txt"

# Очистка файла
> "$OUTPUT_FILE"

echo "Сборка контекста проекта..." >> "$OUTPUT_FILE"
echo "Дата: $(date)" >> "$OUTPUT_FILE"
echo "========================================" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

# Структура проекта
echo "## Структура проекта" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
tree -L 3 -I 'build|.gradle|.idea|out' >> "$OUTPUT_FILE" 2>/dev/null || find . -maxdepth 3 -not -path '*/build/*' -not -path '*/.gradle/*' -not -path '*/.idea/*' >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

# Gradle файлы
echo "## build.gradle / build.gradle.kts" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
for file in $(find . -name "build.gradle*" -not -path "*/build/*"); do
    echo "### $file" >> "$OUTPUT_FILE"
    echo '```gradle' >> "$OUTPUT_FILE"
    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
    echo '```' >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

# Kotlin файлы
echo "## Kotlin файлы" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
for file in $(find . -name "*.kt" -not -path "*/build/*" -not -path "*/.gradle/*"); do
    echo "### $file" >> "$OUTPUT_FILE"
    echo '```kotlin' >> "$OUTPUT_FILE"
    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
    echo '```' >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

# Java файлы
echo "## Java файлы" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
for file in $(find . -name "*.java" -not -path "*/build/*" -not -path "*/.gradle/*"); do
    echo "### $file" >> "$OUTPUT_FILE"
    echo '```java' >> "$OUTPUT_FILE"
    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
    echo '```' >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

# Манифесты и конфигурации
echo "## Конфигурационные файлы" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
for file in $(find . -name "AndroidManifest.xml" -o -name "settings.gradle*" -o -name "gradle.properties" | grep -v build); do
    echo "### $file" >> "$OUTPUT_FILE"
    echo '```xml' >> "$OUTPUT_FILE"
    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
    echo '```' >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

echo "Контекст сохранён в $OUTPUT_FILE"
