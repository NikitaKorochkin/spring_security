# spring_security

## Описание
Простое Spring Security приложение, в котором реализованы следующие возможности:
- процедура аутентификации
- процедура авторизации
- хранение пароля в БД в зашифрованном и не шифрованном видах
---
## Технологии
Spring(MVC, Security), MySQL, Tomcat 9(9.0.44), Maven

---
## Запуск
Для запуска приложения необходимо совершить следующие действия:

1. Клонировать репозиторий с проектом к себе на компьютер:
    
    ![ScreenShot](screenshots/url.png "Копируем URL")
    В командной строке проходим до нужной директории, куда и будет клонирован репозиторий 
    `$ cd YOUR_DIRECTORY`, далее прописываем команду `$ git clone URL`. Репозиторий клонирован.

2. Открыть Intellij Idea: File -> Open -> ... -> spring_security
3. Создать конфигурационный класс в пакете configuration
   
   Создаем класс MyConfig.java в пакете configuration
   ![ScreenShot](screenshots/config.png)

В параметре аннотации @ComponentScan указываем, где Spring должен искать компоненты приложения.

   Для отображения view я испозовал jsp-страницы, поэтому для удобства, опишем бин ViewResolver, назначим суффикс и преффикс для удобного обращения ко view
 ```java
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();

        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;
    }
```
Далее опишем бин DataSource, где мы подключаемся к БД. Рассмотрим подключение MySQL:
 ```java
    @   Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {

            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:"port"/"db_name"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
            dataSource.setUser("username");
            dataSource.setPassword("password");

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
```    
TimeZone прописывать обязательно!

4. Подготовить таблицы в БД
Создадим две таблицы users и authorities и заполним их данными:
```sql
USE your_db;

CREATE TABLE users (
  username varchar(15),
  password varchar(100),
  enabled tinyint(1),
  PRIMARY KEY (username)
) ;

CREATE TABLE authorities (
  username varchar(15),
  authority varchar(25),
  FOREIGN KEY (username) references users(username)
) ;

INSERT INTO my_db.users (username, password, enabled)
VALUES
	('Nikita', '{noop}nikita', 1),
	('Svetlana', '{noop}svetlana', 1),
	('Ivan', '{noop}ivan', 1);
    
INSERT INTO my_db.authorities (username, authority)
VALUES
	('Nikita', 'ROLE_EMPLOYEE'),
	('Elena', 'ROLE_HR'),
    ('Ivan', 'ROLE_HR'),
	('Ivan', 'ROLE_MANAGER');
```
Для хранения пароля в зашифрованном виде, я использовал bcrypt шифрование.
Пример:
Зашифруем пароль для user'a Ivan. Для этого я использовал Bcrypt Password Generator https://www.browserling.com/tools/bcrypt

![](screenshots/bcryptgen.png)

Полученный пароль нужно внести в таблицу и обязательно указать `{bcrypt}`

![](screenshots/bcrypt.png)

5. Настроить Appache Tomcat.

Я использовал Tomcat 9.0.44

В Intellij Idea нажимаем на Edit Configuration рядом с кнопкой запуска, выбираем Tomcat Server -> Local, в появившемся окне нажимаем Configure и находим архив tomcat (скачиваем при необходимости)
![ScreenShot](screenshots/tomcat2.png)

Переходим в Deployment, добавляем артефакт war_exploded

![ScreenShot](screenshots/tomcat3.png)

Нажимаем Apply, OK. Сервер готов.


*Теперь мы можем запустить наше приложение на локальном сервере. 
После успешного запуска вы увидите следующую страницу в браузере и сообщение в консоли*

![](screenshots/final.png)
