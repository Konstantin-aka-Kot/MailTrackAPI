package com.akshentsev.mailtrackapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "MailTrack Api",
                description = "" +
                        "REST API для отслеживания почтовых отправлений. Система предоставляет возможность регистрации и отслеживания передвижения писем и посылок между почтовыми отделениями, а также получения информации о статусе и истории передвижения конкретного отправления. Разные пользователи имеют доступ к различным уровням информации в зависимости от своей роли (клиент, сотрудник почтового отделения, сотрудник распределительного центра или админ).\n" +
                        "\n" +
                        "Основные функции API включают:\n" +
                        "\n" +
                        "1. Регистрация почтового отправления\n" +
                        "2. Зафиксировать прибытие отправления в промежуточное почтовое отделение\n" +
                        "3. Зафиксировать убытие отправления из почтового отделения\n" +
                        "4. Зафиксировать получение отправления адресатом\n" +
                        "5. Просмотр статуса и полной истории движения почтового отправления\n", version = "1.0.0",
                contact = @Contact(
                        name = "Akshentsev Konstantin",
                        email = "akshentsev69@gmail.com",
                        url = "https://t.me/akkonstantin"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
