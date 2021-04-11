# spring_security

## Описание
В данном репозитории представлено простое Spring Security приложение, в котором реализованы следующие возможности:
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
    
    ![ScreenShot](1.png "Копируем URL")
    В командной строке проходим до нужной директории, куда и будет клонирован репозиторий 
    `$ cd YOUR_DIRECTORY`, далее прописываем команду `$ git clone URL`. Репозиторий клонирован.

2. Открыть Intellij Idea: File -> Open -> ... -> spring_security
3. Создать конфигурационный класс в пакете configuration
   
   Создаем класс MyConfig.java в пакете configuration
   ![ScreenShot](2.png)

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
@Bean
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

1. Подготовить таблицы в БД

2. Настроить Appache Tomcat.

Я использовал Tomcat 9.0.44

В Intellij Idea нажимаем на Edit Configuration рядом с кнопкой запуска, выбираем Tomcat Server Local, в появившемся окне нажимаем Configure и находим скачанный архив (скачиваем при необходимости)
![ScreenShot](4.png)
![ScreenShot](5.png)

Переходим в Deployment, добавляем артефакт war_exploded

![ScreenShot](6.png)
![ScreenShot](7.png)

Нажимаем Apply, OK. Сервер готов.

