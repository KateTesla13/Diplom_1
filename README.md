# 🍔 QA-Java-Diplom-1

Юнит-тесты для класса `Burger` проекта **Stellar Burgers**.

---

## 📋 Описание

В рамках дипломного проекта написаны автотесты для класса `Burger`.
Покрытие кода — **100%**.

Используемые технологии:
- **JUnit 4** — запуск тестов
- **Mockito** — моки и стабы
- **JUnitParams** — параметризация
- **JaCoCo** — проверка покрытия

---

## 📁 Структура проекта

```bash
src/
├── main/
│   └── java/
│       └── praktikum/
│           ├── Burger.java
│           ├── Bun.java
│           ├── Ingredient.java
│           ├── IngredientType.java
│           ├── Database.java
│           └── Praktikum.java
└── test/
    └── java/
        └── praktikum/
            ├── BurgerTest.java
            ├── BaseTest.java
            └── BurgerTestData.java
```            
## 🧪 Запуск тестов
```bash

mvn clean test

Отчёт о покрытии (JaCoCo)

mvn clean test jacoco:report
```
👩‍💻 Автор
KateTesla13

## ✅ Статус выполнения
```bash

Клонирован репозиторий
Подключены зависимости
Написаны тесты для Burger
Использованы моки и стабы
Использована параметризация
Покрытие 100% (JaCoCo)
```

