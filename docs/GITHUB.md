# Публикация проекта на GitHub

## Подготовка

1. Распакуй проект, открой его в IntelliJ IDEA и проверь запуск.
2. Запусти тесты в IDE или выполни `mvn clean verify`.
3. Прочитай [разбор кода](LEARNING.md), чтобы понимать устройство проекта.

## Создай репозиторий

На GitHub выбери **New repository**:

- **Repository name:** `java-console-calculator`.
- **Description:** `Java console calculator with BigDecimal arithmetic, input validation, history and JUnit tests.`
- **Visibility:** `Public`, если хочешь, чтобы проект был доступен по ссылке для портфолио.
- Оставь создание README, `.gitignore` и лицензии выключенным: первый коммит
  будет отправлен из уже подготовленной папки.

Нажми **Create repository** и скопируй HTTPS-адрес созданного репозитория.

## Отправь код

Открой терминал IntelliJ IDEA именно в папке `java-console-calculator`, где находится `pom.xml`.
Для команд ниже нужен установленный Git. Проверь его командой `git --version`.

```bash
git init -b main
git add .
git commit -m "Add console calculator with tests and CI"
```

Если Git сообщает, что не знает автора коммита, настрой своё имя и email
через `git config user.name` и `git config user.email`, затем повтори коммит.
Можно использовать приватный `noreply`-адрес из настроек email в GitHub.

Далее замени `YOUR_USERNAME` своим GitHub-логином; если имя репозитория другое,
используй скопированный адрес целиком:

```bash
git remote add origin https://github.com/YOUR_USERNAME/java-console-calculator.git
git push -u origin main
```

Если Git запросит вход, пройди авторизацию GitHub через установленный менеджер
учётных данных или GitHub CLI. Обычный пароль аккаунта не используется для
HTTPS-операций Git; не записывай токены в файлы проекта.

`.gitignore` исключает `target`, `out` и локальные настройки IDEA из коммита.

## Проверь результат

1. Открой страницу репозитория: README должен отображаться под списком файлов.
2. Открой вкладку **Actions** и дождись успешного запуска **Java CI**.
   Проверка выполняется на Java 21 и запускает тесты вместе со сборкой JAR.
3. Добавь темы: `java`, `calculator`, `console-application`, `maven`, `junit`, `java-core`.
4. При желании закрепи репозиторий в своём профиле.

После своего улучшения отправляй изменения отдельным коммитом:

```bash
git add .
git commit -m "Describe the change you made"
git push
```

Замени пример сообщения точным описанием изменения. Например,
`Add remainder operation and zero divisor tests` — после реализации этой функции.

## Короткое описание для портфолио

> Консольный калькулятор на Java 21. Реализованы арифметические операции
> с BigDecimal, проверка пользовательского ввода и история текущей сессии.
> Вычисления отделены от консольного интерфейса. Проект включает тесты JUnit 5,
> сборку Maven и автоматическую проверку в GitHub Actions.

## Официальная инструкция

[Добавление локального проекта в GitHub](https://docs.github.com/en/migrations/importing-source-code/using-the-command-line-to-import-source-code/adding-locally-hosted-code-to-github)
